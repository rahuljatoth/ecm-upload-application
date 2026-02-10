package com.example.EcmUploadApplication;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EcmUploadControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void uploadFile_success() throws Exception {
		MockMultipartFile file =
				new MockMultipartFile(
						"file",
						"test.pdf",
						"application/pdf",
						"dummy file content".getBytes()
				);

		mockMvc.perform(multipart("/ecm/upload")
						.file(file)
						.param("documentType", "INVOICE"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("SUCCESS"));
	}

	@Test
	void uploadFile_missingFile_shouldFail() throws Exception {
		mockMvc.perform(multipart("/ecm/upload"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value("FAILED"));
	}

}

@Test
void uploadFile_unsupportedType_shouldFail() throws Exception {
	MockMultipartFile file =
			new MockMultipartFile(
					"file",
					"script.exe",
					"application/octet-stream",
					"bad file".getBytes()
			);

	mockMvc.perform(multipart("/ecm/upload").file(file))
			.andExpect(status().isUnsupportedMediaType())
			.andExpect(jsonPath("$.status").value("FAILED"));
}

@Test
void uploadFile_tooLarge_shouldFail() throws Exception {
	byte[] largeFile = new byte[10 * 1024 * 1024]; // 10MB

	MockMultipartFile file =
			new MockMultipartFile(
					"file",
					"large.pdf",
					"application/pdf",
					largeFile
			);

	mockMvc.perform(multipart("/ecm/upload").file(file))
			.andExpect(status().isPayloadTooLarge())
			.andExpect(jsonPath("$.status").value("FAILED"));
}



}
