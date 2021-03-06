package lotto;

import static lotto.utils.StringConverter.convertTo;
import static lotto.view.InputView.inputBonusLottoNumber;
import static lotto.view.InputView.inputPurchaseManualLottoCount;
import static lotto.view.InputView.inputPurchasePrice;
import static lotto.view.InputView.inputPurchasedManualTicketsByCount;
import static lotto.view.InputView.inputWinningLottoNumber;
import static lotto.view.OutputView.printLottoNumber;
import static lotto.view.OutputView.printLottoStatistics;
import static lotto.view.OutputView.printProfitResult;

import lotto.model.LottoNumber;
import lotto.model.LottoResults;
import lotto.model.LottoStore;
import lotto.model.Payment;
import lotto.model.PurchaseInfo;
import lotto.model.PurchasedLottoTickets;
import lotto.model.WinningLottoTicket;

public class Main {
    public static void main(String[] args) {
        int purchasePrice = inputPurchasePrice();
        int purchaseManualLottoCount = inputPurchaseManualLottoCount();

        PurchasedLottoTickets manualLottoTickets = PurchasedLottoTickets.create(inputPurchasedManualTicketsByCount(purchaseManualLottoCount));
        PurchasedLottoTickets automaticLottoTickets = LottoStore.sell(Payment.of(purchasePrice, manualLottoTickets.count()));

        PurchaseInfo purchaseInfo = PurchaseInfo.create(manualLottoTickets, automaticLottoTickets);
        printLottoNumber(purchaseInfo);

        String inputWinningLottoNumberString = inputWinningLottoNumber();
        int inputBonusLottoNumber = inputBonusLottoNumber();
        WinningLottoTicket winningLottoTicket = WinningLottoTicket.createBy(
            convertTo(inputWinningLottoNumberString), LottoNumber.of(inputBonusLottoNumber)
        );

        LottoResults lottoResults = winningLottoTicket.match(purchaseInfo.getAllTickets());
        printLottoStatistics(lottoResults);
        printProfitResult(lottoResults.getProfit());
    }
}
