// package com.manish.excel_import_export.service;

// import java.io.ByteArrayInputStream;
// import java.io.IOException;
// import java.io.InputStream;
// import java.nio.charset.StandardCharsets;
// import java.util.List;

// import org.springframework.http.HttpInputMessage;
// import org.springframework.http.HttpOutputMessage;
// import org.springframework.http.MediaType;
// import org.springframework.http.converter.AbstractHttpMessageConverter;
// import org.springframework.http.converter.HttpMessageConverter;
// import org.springframework.http.converter.HttpMessageNotReadableException;
// import org.springframework.http.converter.HttpMessageNotWritableException;
// import org.springframework.stereotype.Component;

// @Component
// public class ByteArrayInputStreamHttpMessageConverter extends AbstractHttpMessageConverter<ByteArrayInputStream> {

//     public ByteArrayInputStreamHttpMessageConverter() {
//         super(MediaType.APPLICATION_OCTET_STREAM);
//     }

//     @Override
//     protected boolean supports(Class<?> clazz) {
//         return ByteArrayInputStream.class.equals(clazz);
//     }

//     @Override
//     protected ByteArrayInputStream readInternal(Class<? extends ByteArrayInputStream> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
//         return new ByteArrayInputStream(inputMessage.getBody());
//     }

//     @Override
//     protected void writeInternal(ByteArrayInputStream byteArrayInputStream, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
//         outputMessage.getBody().write(byteArrayInputStream.readAllBytes());
//     }
// }