package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import db.WifiManager;

public class WifiService {
		
	public void saveAll() throws IOException {
		WifiManager wm = new WifiManager();
		wm.createTable();
		wm.clean();
		wm.insertAll(totalNum());
	}

	public int totalNum() throws IOException {
		StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088");
		urlBuilder.append("/" +  URLEncoder.encode("44476967516c736232355371566d61","UTF-8") );
		urlBuilder.append("/" +  URLEncoder.encode("json","UTF-8") );
		urlBuilder.append("/" + URLEncoder.encode("TbPublicWifiInfo","UTF-8"));
		urlBuilder.append("/" + URLEncoder.encode("1","UTF-8"));
		urlBuilder.append("/" + URLEncoder.encode("1","UTF-8"));


		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");

		BufferedReader rd;

		if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
				rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
				rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
				sb.append(line);
		}
		
		rd.close();
		conn.disconnect();
		
		Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(sb.toString(), JsonObject.class);
        int total = jsonObject.getAsJsonObject("TbPublicWifiInfo").get("list_total_count").getAsInt();
        
        return total;
	}
}