package com.lezo.rest.baidu.pcs;

import com.baidu.pcs.BaiduPCSActionInfo.PCSFileInfoResponse;
import com.baidu.pcs.BaiduPCSClient;

public class ApiPcsClient {
	public static void main(String[] args) throws Exception {
		String access_token = "23.667bbb2d701b083d956a61df15a6ff09.2592000.1427599070.4026763474-2920106&session_secret=3dcab0b540c170807f41fe12dc025e18&";
//		String url = "https://pcs.baidu.com/rest/2.0/pcs/file";
		String source = "src/main/resources/region.txt";
		String path = "/apps/emao_doc/region.txt";
		BaiduPCSClient client = new BaiduPCSClient(access_token);
		PCSFileInfoResponse res = client.uploadFile(source, path);
		System.out.println(res.status.message);
		System.out.println(res.commonFileInfo.fsId);
		System.out.println(res.commonFileInfo.path);
	}
}
