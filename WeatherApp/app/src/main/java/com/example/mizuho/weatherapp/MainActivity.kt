package com.example.mizuho.weatherapp

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import android.widget.ArrayAdapter
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.AdapterView.OnItemSelectedListener
import com.example.mizuho.weatherapp.R.id.spinner
import android.widget.TextView
import android.view.ViewGroup
import com.beust.klaxon.Json
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser


private const val TAG: String = "MAIN_ACTIVITY"
private const val REQUEST_PERMISSION = 1000


class MainActivity : AppCompatActivity() {

    val place_name = arrayOf("道北", "稚内", "旭川", "留萌", "道東", "網走", "北見", "紋別",
        "根室", "釧路", "帯広", "道南", "室蘭", "浦河", "道央", "札幌", "岩見沢", "倶知安", "道南",
        "函館", "江差", "青森県", "青森", "むつ", "八戸", "岩手県", "盛岡", "宮古", "大船渡", "宮城県",
        "仙台", "白石", "秋田県", "秋田", "横手", "山形県", "山形", "米沢", "酒田", "新庄", "福島県",
        "福島", "小名浜", "若松", "茨城県", "水戸", "土浦", "栃木県", "宇都宮", "大田原", "群馬県",
        "前橋", "みなかみ", "埼玉県", "さいたま", "熊谷", "秩父", "千葉県", "千葉", "銚子", "館山",
        "東京都", "東京", "大島", "八丈島", "父島", "神奈川県", "横浜", "小田原", "新潟県", "新潟",
        "長岡", "高田", "相川", "富山県", "富山", "伏木", "石川県", "金沢", "輪島", "福井県", "福井",
        "敦賀", "山梨県", "甲府", "河口湖", "長野県", "長野", "松本", "飯田", "岐阜県", "岐阜", "高山",
        "静岡県", "静岡", "網代", "三島", "浜松", "愛知県", "名古屋", "豊橋", "三重県", "津", "尾鷲",
        "滋賀県", "大津", "彦根", "京都府", "京都", "舞鶴", "大阪府", "大阪", "兵庫県", "神戸", "豊岡",
        "奈良県", "奈良", "風屋", "和歌山県", "和歌山", "潮岬", "鳥取県", "鳥取", "米子", "島根県", "松江",
        "浜田", "西郷", "岡山県", "岡山", "津山", "広島県", "広島", "庄原", "山口県", "下関", "山口",
        "柳井", "萩", "徳島県", "徳島", "日和佐", "香川県", "高松", "愛媛県", "松山", "新居浜", "宇和島",
        "高知県", "高知", "室戸岬", "清水", "福岡県", "福岡", "八幡", "飯塚", "久留米", "佐賀県", "佐賀",
        "伊万里", "長崎県", "長崎", "佐世保", "厳原", "福江", "熊本県", "熊本", "阿蘇乙姫", "牛深", "人吉",
        "大分県", "大分", "中津", "日田", "佐伯", "宮崎県", "宮崎", "延岡", "都城", "高千穂", "鹿児島県",
        "鹿児島", "鹿屋", "種子島", "名瀬", "沖縄県", "那覇", "名護", "久米島", "南大東", "宮古島", "石垣島", "与那国島"
    )

    val place_id = arrayOf("-1", "011000", "012010", "012020", "-1", "013010", "013020", "013030",
        "014010", "014020", "014030", "-1", "015010", "015020", "-1", "016010", "016020", "016030",
        "-1", "017010", "017020", "-1", "020010", "020020", "020030", "-1", "030010", "030020",
        "030030", "-1", "040010", "040020", "-1", "050010", "050020", "-1", "060010", "060020",
        "060030", "060040", "-1", "070010", "070020", "070030", "-1", "080010", "080020", "-1",
        "090010", "090020", "-1", "100010", "100020", "-1", "110010", "110020", "110030", "-1",
        "120010", "120020", "120030", "-1", "130010", "130020", "130030", "130040", "-1", "140010",
        "140020", "-1", "150010", "150020", "150030", "150040", "-1", "160010", "160020", "-1",
        "170010", "170020", "-1", "180010", "180020", "-1", "190010", "190020", "-1", "200010",
        "200020", "200030", "-1", "210010", "210020", "-1", "220010", "220020", "220030", "220040",
        "-1", "230010", "230020", "-1", "240010", "240020", "-1", "250010", "250020", "-1", "260010",
        "260020", "-1", "270000", "-1", "280010", "280020", "-1", "290010", "290020", "-1", "300010",
        "300020", "-1", "310010", "310020", "-1", "320010", "320020", "320030", "-1", "330010",
        "330020", "-1", "340010", "340020", "-1", "350010", "350020", "350030", "350040", "-1",
        "360010", "360020", "-1", "370000", "-1", "380010", "380020", "380030", "-1", "390010",
        "390020", "390030", "-1", "400010", "400020", "400030", "400040", "-1", "410010", "410020",
        "-1", "420010", "420020", "420030", "420040", "-1", "430010", "430020", "430030", "430040",
        "-1", "440010", "440020", "440030", "440040", "-1", "450010", "450020", "450030", "450040",
        "-1", "460010", "460020", "460030", "460040", "-1", "471010", "471020", "471030", "472000",
        "473000", "474010", "474020 ")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // パーミッションの確認
        checkPermission()

        textView.text = "知りたい地域を選んでください"

        // spinner の設定
        // spinnerで選択可能項目の先頭に空白を追加
        for (i in 0 until place_id.size) {
            if(place_id[i] != "-1")
                place_name[i] = "    ${place_name[i]}"
        }

        //
        val adapter = object: ArrayAdapter<String>(
            applicationContext,
            android.R.layout.simple_spinner_item,
            place_name
        ) {
            // place_id が -1 の項目は選択不可にする
            override fun isEnabled(position: Int): Boolean {
                return place_id[position] != "-1"
            }
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setSelection(1)


        // button の設定
        button.text = "検索"
        button.setOnClickListener{ view ->
            Log.d(TAG, "click button")

//            GlobalScope.launch {
            runBlocking {   // メインスレッドで実行
                var sentence = ""

                runBlocking(Dispatchers.Default) { // 新しく立てたスレッド内で実行
                    Log.d(TAG,  "working in thread ${Thread.currentThread().name}")

                    val spinnerId = spinner.selectedItemId.toInt()
                    val id  = place_id[spinnerId]
                    val url = URL("http://weather.livedoor.com/forecast/webservice/json/v1?city=$id")
                    val res = sentHttpRequest(url)

                    Log.d(TAG, res)
                    sentence =  jsonToSentence(res)
                    Log.d(TAG, "sentence:\n $sentence")
                }

                textView.text = sentence
            }
        }
    }


    private fun sentHttpRequest(url: URL): String {
        Log.d(TAG, "start SentHttpRequest")

        val urlConnection = url.openConnection() as HttpURLConnection
        urlConnection.requestMethod = "GET"
        // 接続タイムアウト時間
        urlConnection.connectTimeout = 100000
        // レスポンスのデータの読み取りタイムアウト時間
        urlConnection.readTimeout = 100000
        urlConnection.setRequestProperty("accept", "application/json")
        urlConnection.addRequestProperty("Content-Type", "Raw; charset=UTF-8")
        //リクエストのボディ送信の可否
        urlConnection.doOutput = false
        //レスポンスのボディ受信の可否
        urlConnection.doInput = true
        // リダイレクトの可否
        urlConnection.instanceFollowRedirects = true

        urlConnection.connect()


        val responseCode = urlConnection.responseCode
        Log.d(TAG, "HttpStatusCode:$responseCode")
        val br = BufferedReader(InputStreamReader(urlConnection.inputStream))
        val sb = StringBuilder()

        for (line: String? in br.readLines()) {
            line?.let { sb.append(line) }
        }

        br.close()

//        Log.d(TAG, sb.toString())

        return sb.toString()
    }


    private fun jsonToSentence(str: String): String {

        var sentence = ""

        // stringをjsonに変換
        val parser: Parser = Parser.default()
        val stringBuilder  = StringBuilder(str)
        val json: JsonObject = parser.parse(stringBuilder) as JsonObject

        // jsonから必要なデータを取得する
//        val pinpointLocations = json.array<JsonObject>("pinpointLocations")
//        val link: String?     = json.string("link")
        val forecasts         = json.array<JsonObject>("forecasts")

        if (forecasts == null)
            return ""

        for(i in 0 until forecasts.size) {
            val dateLabel = forecasts[i].string("dateLabel")
            val telop     = forecasts[i].string("telop")
//            val date      = forecasts[i].string("date")
            val temperature = forecasts[i].obj("temperature")

            if (temperature == null) {
                sentence = """"$sentence
                |$dateLabel の天気は $telop
                |
                """.trimMargin("|")
                continue
            }

            // 最低気温、最高気温の取得
            val min = temperature.obj("min")
            val max = temperature.obj("max")
            var minCelsius: String? = ""
            var maxCelsius: String? = ""

            if (min != null)
                minCelsius = min.string("celsius")

            if (max != null)
                maxCelsius = max.string("celsius")

            // sentenceの更新
            sentence = """$sentence
                |$dateLabel の天気は $telop
                |最高気温は $maxCelsius
                |最低気温は $minCelsius
                |
                """.trimMargin("|")
        }

        return sentence
    }


    /*
     * パーミッションの確認
     */

    // permissionの確認
    fun checkPermission() {

        Log.d(TAG, "check permission")

        // 既に許可している
        if (ActivityCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            // do something

        } else {
            requestLocationPermission()
        }// 拒否していた場合
    }

    // 許可を求める
    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this@MainActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_PERMISSION
            )

        } else {
            Toast.makeText(this@MainActivity, "アプリ実行に許可が必要です", Toast.LENGTH_SHORT).show()

            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_PERMISSION
            )
        }
    }

    // 結果の受け取り
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        if (requestCode == REQUEST_PERMISSION) {
            // 使用が許可された
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // do something

            } else {
                // それでも拒否された時の対応
                Toast.makeText(this, "cannot do anything", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
