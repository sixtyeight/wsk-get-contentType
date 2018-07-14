package com.example;

import java.io.IOException;
import java.util.Base64;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.google.gson.JsonObject;

/**
 * Hello FunctionApp
 */
public class GetMediaTypeApp {

	private static TikaConfig tika;

	private static TikaConfig getTika() throws TikaException, IOException {
		if (tika == null) {
			synchronized (GetMediaTypeApp.class) {
				if (tika == null) {
					tika = new TikaConfig();
				}
			}
		}

		return tika;
	}

	public static JsonObject main(JsonObject args) throws IOException, TikaException {
		JsonObject response = new JsonObject();

		MediaType mediaType = getTika().getDetector().detect(
				TikaInputStream.get(Base64.getDecoder().decode(args.get("document").getAsString())), new Metadata());

		response.addProperty("mediaType", mediaType.toString());

		return response;
	}

}
