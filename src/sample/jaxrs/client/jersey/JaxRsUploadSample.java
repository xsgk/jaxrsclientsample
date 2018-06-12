package sample.jaxrs.client.jersey;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.Date;
import java.util.HashMap;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;

import sample.jaxrs.client.jersey.util.Log;

/*--------------------------------------------------*/
/**
 * Sample JAX-RS client using Jersey.
 */
/*--------------------------------------------------*/
public class JaxRsUploadSample {

	private final String JSON_REQUESTID = "requestid";
	private final String JSON_UPLOADDAT = "uploaddat";

	private static final String SAVENAME_PATTERN = "yyyymmddhhMMss";

	/*--------------------------------------------------*/
	/**
	 * エンドポイントへリクエストを送信する
	 * @param endopointhost 宛先ホスト
	 * @param endpointfile 宛先リソース
	 * @param base64encdat 送信バイナリデータ（Base64エンコード文字列）
	 * @return 応答結果を格納したハッシュマップ
	 * @throws Exception
	 */
	/*--------------------------------------------------*/
	public HashMap<String, String> sendPost(String endopointhost, String endpointfile, String base64encdat)
			throws Exception {

		Log.out("Sending request to: " + endopointhost + endpointfile);
		Client client = ClientBuilder.newClient();

		Entity<JsonObject> entity = Entity.json(buildJson(base64encdat));

		HashMap<String, String> result = client.target(endopointhost)
				.path(endpointfile)
				.request()
				.post(entity, HashMap.class);

		return result;
	}

	/*--------------------------------------------------*/
	/**
	 * JSON形式のリクエストボディを生成
	 * @param base64encdat Base64エンコードされたバイナリデータ
	 * @return
	 */
	/*--------------------------------------------------*/
	protected JsonObject buildJson(String base64encdat) {
		JsonObject json = Json.createObjectBuilder()
				.add(JSON_REQUESTID, generateRequestID())
				.add(JSON_UPLOADDAT, base64encdat)
				.build();
		Log.out(json.toString().substring(0, 100) + " ... ");
		return json;
	}

	/*--------------------------------------------------*/
	/**
	 * リクエストIDの生成
	 * @return リクエストID
	 */
	/*--------------------------------------------------*/
	protected String generateRequestID() {
		return Long.toString(System.currentTimeMillis());
	}

	/*--------------------------------------------------*/
	/**
	 *  サンプル実行用メイン
	 */
	/*--------------------------------------------------*/
	public static void main(String[] args) {

		String endpointhost = "http://jaxrsresourcesample:9080";
		String endpointfile = "/jaxrs/resourcesample";
		String uploadfile = "/opt/jaxrsclientsample/upload/upload.png";
		String savefileto = "/opt/jaxrsclientsample/download";
		String json_downloaddat = "downloaddat";

		try {
			// アップロードファイルデータを作成
			Log.out("Create uploadfile: " + uploadfile);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			FileInputStream fis = new FileInputStream(uploadfile);
			int b = 0;
			while ((b = fis.read()) != -1) {
				os.write(b);
			}
			fis.close();

			// 読み込んだバイト配列を Base64 エンコードした文字列に変換
			Log.out("Encoding upload data with Base64");
			Encoder encoder = Base64.getUrlEncoder();
			String updloaddat = encoder.encodeToString(os.toByteArray());

			// サービスに対してリクエストを送信、結果を HashMap で取得
			Log.out("Sending request...");
			JaxRsUploadSample jaxrs = new JaxRsUploadSample();
			HashMap<String, String> result = jaxrs.sendPost(endpointhost, endpointfile, updloaddat);

			// 応答に含まれるバイナリデータをファイルに保存
			String savedataas = savefileto + "/" + getSaveFileName() + ".png";
			Log.out("Saving download data as: " + savedataas);
			Decoder decoder = Base64.getUrlDecoder();
			byte[] binary = decoder.decode((String) result.get(json_downloaddat));
			FileOutputStream fos = new FileOutputStream(savedataas);
			fos.write(binary);
			fos.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*--------------------------------------------------*/
	/**
	 * アップロードデータの保存ファイル名を生成する
	 * @return 保存ファイル名
	 */
	/*--------------------------------------------------*/
	private static String getSaveFileName() {
		SimpleDateFormat df = new SimpleDateFormat(SAVENAME_PATTERN);
		return df.format(new Date());
	}

}
