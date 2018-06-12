package sample.jaxrs.client.jersey.util;

import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.Date;

/*---------------------------------------------------------------------------*/
/**
 * 簡易ロガークラス
 */
/*---------------------------------------------------------------------------*/
public class Log {

	/** ログ出力先。デフォルトは標準出力。 */
	private static PrintStream ps = System.out;

	/** ログ書式。 */
	private static final MessageFormat mf = new MessageFormat(
			"{0, date,[yyyy-MM-dd HH:mm:ss:SSS]} {1}");

	/*---------------------------------------------------------------------------*/
	/**
	 * ログ出力
	 * @param msg
	 *            デバッグメッセージ
	 */
	/*---------------------------------------------------------------------------*/
	public static void out(String msg) {
		synchronized (ps) {
			Object[] obj = { new Date(), msg };
			ps.println(mf.format(obj));
		}
	}

}
