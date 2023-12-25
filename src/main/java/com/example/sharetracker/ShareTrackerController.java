package com.example.sharetracker;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.tinkoff.piapi.contract.v1.*;
import ru.tinkoff.piapi.core.InvestApi;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
/**
 * Контроллер ShareTrackerController управляет взаимодействием между пользовательским интерфейсом JavaFX и API для отслеживания ценных бумаг.
 * Он содержит методы для поиска и отображения ценных бумаг, а также управления закладками.
 */
public class ShareTrackerController {
    @FXML
    private TextField searchField;

    @FXML
    private TableView<ShareInfo> sharesTable;

    @FXML
    private TableColumn<ShareInfo, Boolean> bookmarkColumn;

    private final Set<String> savedFigi = new HashSet<>();
    private final String token = "t._UnQBPxxIB6HBnGkSZFQ18jMkKW5s_WVuljeHMpcDUM_qni-EmvBsvXb1P398tJTs-F2meWyuCBUdcfVfvXoaQ";
    private final InvestApi api = InvestApi.createReadonly(token);

    public static double minuteCandle(InvestApi api, String figi) {
        var candles1min = api.getMarketDataService()
                .getCandlesSync(figi, Instant.now().minus(1, ChronoUnit.DAYS), Instant.now(),
                        CandleInterval.CANDLE_INTERVAL_1_MIN);
        double openValue = 0;
        for (HistoricCandle candle : candles1min) {
            openValue = candle.getClose().getUnits() + candle.getClose().getNano() / 1_000_000_000.0;
        }
        return openValue;
    }

    @FXML
    public void initialize() {
        // Устанавливаем свойства значений в столбце "Bookmark"
        bookmarkColumn.setCellValueFactory(new PropertyValueFactory<>("bookmarked"));
        bookmarkColumn.setCellFactory(column -> new TableCell<>() {
            private final CheckBox checkBox = new CheckBox();

            {
                checkBox.setOnAction(event -> {
                    ShareInfo shareInfo = getTableView().getItems().get(getIndex());
                    if (checkBox.isSelected()) {
                        savedFigi.add(shareInfo.getFigi());
                    } else {
                        savedFigi.remove(shareInfo.getFigi());
                    }
                });
            }

            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    checkBox.setSelected(item);
                    setGraphic(checkBox);
                }
            }
        });
    }

    @FXML
    public void searchShares() {
        String name = searchField.getText();

        // Очистка таблицы перед новым поиском
        sharesTable.getItems().clear();

        shareData(name, false);
        // Добавим setCellValueFactory для каждого столбца
        setCellValueColumn();
    }

    private void setCellValueColumn() {
        TableColumn<ShareInfo, String> figiColumn = (TableColumn<ShareInfo, String>) sharesTable.getColumns().get(0);
        TableColumn<ShareInfo, String> nameColumn = (TableColumn<ShareInfo, String>) sharesTable.getColumns().get(1);
        TableColumn<ShareInfo, String> tickerColumn = (TableColumn<ShareInfo, String>) sharesTable.getColumns().get(2);
        TableColumn<ShareInfo, Double> costColumn = (TableColumn<ShareInfo, Double>) sharesTable.getColumns().get(3);

        figiColumn.setCellValueFactory(new PropertyValueFactory<>("figi"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        tickerColumn.setCellValueFactory(new PropertyValueFactory<>("ticker"));
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));

        for (ShareInfo shareInfo : sharesTable.getItems()) {
            // Добавляем слушатель для изменений в закладках
            shareInfo.bookmarkedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    savedFigi.add(shareInfo.getFigi());
                } else {
                    savedFigi.remove(shareInfo.getFigi());
                }
            });
        }
    }

    @FXML
    public void viewBookmarks() {
        // Очистка таблицы перед новым отображением закладок
        sharesTable.getItems().clear();

        for (String figi : savedFigi) {
            shareData(figi, true);
        }
        // Добавим setCellValueFactory для каждого столбца
        setCellValueColumn();
    }

    private void shareData(String figi, boolean isBookmarked) {
        List<InstrumentShort> instruments = api.getInstrumentsService().findInstrumentSync(figi);
        for (InstrumentShort instrument : instruments) {
            String fi = instrument.getFigi();
            if (Objects.equals(String.valueOf(instrument.getInstrumentKind()), "INSTRUMENT_TYPE_SHARE") && (minuteCandle(api, instrument.getFigi()) != 0) && (!fi.startsWith("TC"))) {
                ShareInfo shareInfo;
                if (isBookmarked) {
                    shareInfo = ShareInfoFactory.createBookmarkedShareInfo(fi, instrument.getName(), instrument.getTicker(), minuteCandle(api, instrument.getFigi()));
                } else {
                    shareInfo = ShareInfoFactory.createShareInfo(fi, instrument.getName(), instrument.getTicker(), minuteCandle(api, instrument.getFigi()));
                }
                sharesTable.getItems().add(shareInfo);
            }
        }
    }
}
