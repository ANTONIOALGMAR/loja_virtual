package com.antonio.lojavirtual;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.antonio.lojavirtual.model.dto.ObjetoErroDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controlador global para tratamento de exceções.
 * Centraliza a captura e tratamento de erros na aplicação.
 */
@RestControllerAdvice
public class ControleExcecoes extends ResponseEntityExceptionHandler {

    // Logger para registrar mensagens e rastreamento de erros
    private static final Logger logger = LoggerFactory.getLogger(ControleExcecoes.class);

    /**
     * Trata exceções personalizadas lançadas pela aplicação.
     *
     * @param ex Instância da exceção customizada.
     * @return Resposta com o erro formatado em ObjetoErroDTO.
     */
    @ExceptionHandler(ExceptionMentoriaJava.class)
    public ResponseEntity<Object> handleExceptionCustom(ExceptionMentoriaJava ex) {
        ObjetoErroDTO objetoErroDTO = new ObjetoErroDTO();
        objetoErroDTO.setError(ex.getMessage());
        objetoErroDTO.setCode(HttpStatus.OK.toString());

        // Registra o erro no log
        logger.error("Erro customizado: {}", ex.getMessage(), ex);
        return ResponseEntity.ok(objetoErroDTO);
    }

    /**
     * Trata exceções genéricas e erros de validação que ocorrem durante o processamento.
     *
     * @param ex      Exceção capturada.
     * @param body    Corpo da resposta (não usado aqui).
     * @param headers Cabeçalhos da resposta.
     * @param status  Código HTTP associado à exceção.
     * @param request Informações da requisição que causou o erro.
     * @return Resposta com detalhes do erro em ObjetoErroDTO.
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ObjetoErroDTO objetoErroDTO = new ObjetoErroDTO();
        String msg = "";

        // Tratamento de exceções de validação de argumentos
        if (ex instanceof MethodArgumentNotValidException) {
        	
            List<ObjectError> list = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();
            
            for (ObjectError objectError : list) {
            	
            	msg += objectError.getDefaultMessage() + "\n";
            }
            	
        } 
        // Tratamento de erros de leitura no corpo da requisição
        if (ex instanceof HttpMessageNotReadableException) {
            msg = "Não está sendo enviado dados no corpo da requisição (BODY).";
        } 
        // Tratamento genérico para outras exceções
        else {
            msg = ex.getMessage();
        }

        objetoErroDTO.setError(msg);
        objetoErroDTO.setCode(status.value() + " - " + status.getReasonPhrase());

        // Registra o erro no log
        logger.error("Erro interno: {}", msg, ex);
        return new ResponseEntity<>(objetoErroDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Trata exceções relacionadas a integridade e operações no banco de dados.
     *
     * @param ex Exceção de integridade ou banco de dados.
     * @return Resposta com detalhes do erro em ObjetoErroDTO.
     */
    @ExceptionHandler({DataIntegrityViolationException.class, ConstraintViolationException.class, SQLException.class})
    protected ResponseEntity<Object> handleExceptionDataIntegry(Exception ex) {
        ObjetoErroDTO objetoErroDTO = new ObjetoErroDTO();
        String msg;

        // Tratamento de erros de integridade de dados
        if (ex instanceof DataIntegrityViolationException) {
            msg = "Erro de integridade no banco: " + getCauseMessage(ex);
        } 
        // Tratamento de erros de violação de chave estrangeira
        else if (ex instanceof ConstraintViolationException) {
            msg = "Erro de chave estrangeira: " + getCauseMessage(ex);
        } 
        // Tratamento de erros gerais de SQL
        else if (ex instanceof SQLException) {
            msg = "Erro de SQL no banco: " + getCauseMessage(ex);
        } 
        // Tratamento genérico para outras exceções de banco
        else {
            msg = ex.getMessage();
        }

        objetoErroDTO.setError(msg);
        objetoErroDTO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());

        // Registra o erro no log
        logger.error("Erro de banco de dados: {}", msg, ex);
        return new ResponseEntity<>(objetoErroDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Obtém a mensagem da causa raiz da exceção.
     *
     * @param ex Exceção capturada.
     * @return Mensagem detalhada da causa raiz.
     */
    private String getCauseMessage(Throwable ex) {
        Throwable cause = ex.getCause();
        while (cause != null && cause.getCause() != null) {
            cause = cause.getCause();
        }
        return cause != null ? cause.getMessage() : ex.getMessage();
    }
}

