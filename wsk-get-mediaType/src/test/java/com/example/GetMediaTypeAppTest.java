package com.example;

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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import org.apache.commons.io.IOUtils;
import org.apache.tika.exception.TikaException;
import org.junit.Test;

import com.google.gson.JsonObject;

/**
 * Unit test for simple function.
 */
public class GetMediaTypeAppTest {

	@Test
	public void testRandom() throws IOException, TikaException {
		test("file.bin", "application/octet-stream");
	}
	
	@Test
	public void testPdf() throws IOException, TikaException {
		test("file.pdf", "application/pdf");
	}

	@Test
	public void testPng() throws IOException, TikaException {
		test("file.png", "image/png");
	}

	@Test
	public void testPlain() throws IOException, TikaException {
		test("file.txt", "text/plain");
	}

	@Test
	public void testGif() throws IOException, TikaException {
		test("file.gif", "image/gif");
	}

	@Test
	public void testJpg() throws IOException, TikaException {
		test("file.jpg", "image/jpeg");
	}

	@Test
	public void testXls() throws IOException, TikaException {
		test("file.xls", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	}

	@Test
	public void testJson() throws IOException, TikaException {
		test("file.json", "text/plain");
	}

	private void test(String resourceName, String expectedMediaType) throws IOException, TikaException {
		JsonObject args = new JsonObject();

		try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName)) {
			args.addProperty("document", Base64.getEncoder().encodeToString(IOUtils.toByteArray(in)));
		}

		JsonObject response = GetMediaTypeApp.main(args);
		assertNotNull(response);
		String mediaType = response.getAsJsonPrimitive("mediaType").getAsString();
		assertNotNull(mediaType);
		assertEquals(expectedMediaType, mediaType);
	}

}
