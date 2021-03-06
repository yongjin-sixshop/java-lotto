package step2.view;

import java.util.Map;
import java.util.stream.Collectors;
import step2.model.LottoResult;
import step2.model.LottoResults;
import step2.model.PurchasedLottoTicket;
import step2.model.PurchasedLottoTickets;

public class OutputView {

    private static final String PURCHASED_COUNT_FORMAT = "%d개를 구매했습니다.";
    private static final String RESULT_FORMAT = "%d개 일치 (%d원) - %d개";
    private static final String PROFIT_FORMAT = "총 수익률은 %.2f입니다.";
    private static final String PROFIT_WITH_LOSS_FORMAT = PROFIT_FORMAT + "(기준이 1이기 때문에 결과적으로 손해라는 의미임)";
    private static final String PRIZE_STATICS_TEXT = "당첨 통계";
    private static final String DIVISION_LINE = "---------";
    private static final String DELIMITER = "\n";

    public static void printLottoNumber(PurchasedLottoTickets purchasedLottoTickets) {
        System.out.println(String.format(PURCHASED_COUNT_FORMAT, purchasedLottoTickets.size()));
        System.out.println(printPurchasedLottoTickets(purchasedLottoTickets));
        System.out.println();
    }

    private static String printPurchasedLottoTickets(PurchasedLottoTickets purchasedLottoTickets) {
        return purchasedLottoTickets.getPurchasedLottoTickets().stream()
            .map(PurchasedLottoTicket::toString)
            .collect(Collectors.joining(DELIMITER));
    }

    public static void printLottoStatistics(LottoResults lottoResults) {
        System.out.println();
        System.out.println(PRIZE_STATICS_TEXT);
        System.out.println(DIVISION_LINE);
        Map<LottoResult, Long> printResult = lottoResults.getResult();

        printResult.keySet()
            .stream()
            .filter(lottoResult -> !lottoResult.isZeroPrice())
            .forEach(lottoResult -> printWinResult(lottoResult, lottoResults.count(lottoResult)));
    }

    private static void printWinResult(LottoResult lottoResult, Long resultCount) {
        System.out.println(String.format(RESULT_FORMAT, lottoResult.getMatchCount(), lottoResult.getPrice(), resultCount));
    }

    public static void printProfitResult(Double profit) {
        if (profit < 1) {
            System.out.println(String.format(PROFIT_WITH_LOSS_FORMAT, profit));
            return;
        }
        System.out.println(String.format(PROFIT_FORMAT, profit));
    }
}
