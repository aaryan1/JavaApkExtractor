package ApkAnalyzer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import apkData.ApkManifestData;

public class ReadYml {
	public ReadYml() throws IOException {
		String location = System.getProperty("user.dir") + "/Decompiled/apktool.yml";

		ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

		Map<String, List<Object>> user =
				objectMapper.readValue(new File(location), Map.class);

		for(Map.Entry<String, List<Object>> entry : user.entrySet()) {
			if(entry.getKey().equalsIgnoreCase("sdkInfo"))
			{
				ApkManifestData.setSdkInfo("sdkInfo "+entry.getValue());
			}
			if(entry.getKey().equalsIgnoreCase("versionInfo"))
			{
				ApkManifestData.setApkInfo("versionInfo "+entry.getValue());
			}

		}
	}

}
