package tests;

import communication.Header;
import communication.messages.LoginMessage;
import communication.messages.Message;
import communication.responses.LoginResponse;
import communication.responses.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageTests {

    @Test
    public void test_login_messages(){

        String username = "David";
        String password = "abcd1234";

        /* Testar criar mensagem*/
        String expected_message = Header.LOGIN + Header.SEPARATOR + username + Header.SEPARATOR + password;
        String message = new LoginMessage(username, password).toString();

        assertEquals(expected_message, message);

        /* Testar interpretar mensagem*/
        Message received_message = Message.parse(message);

        assert(received_message instanceof LoginMessage);
        assertEquals(username, ((LoginMessage) received_message).getUsername());
        assertEquals(password, ((LoginMessage) received_message).getPassword());

        /* Testar enviar mensagem com campos vazios */
        String expected_message1 = Header.LOGIN + Header.SEPARATOR + "" + Header.SEPARATOR + "";
        String message1 = new LoginMessage("", "").toString();

        assertEquals(expected_message1, message1);

        /* Testar interpretar mensagem com campos vazios */
        Message received_message1 = Message.parse(message1);

        assert(received_message1 instanceof LoginMessage);
        assertEquals(Header.ERROR, received_message1.getType());

    }

    @Test
    public void test_login_responses(){

        String result = Header.SUCCESS;
        String certeficate = "afgh35g2jh52hjk23kh34663t";

        /* Testar criar resposta*/
        String expected_message = Header.LOGIN + Header.SEPARATOR + result + Header.SEPARATOR + certeficate;
        String response = new LoginResponse(result, certeficate).toString();

        assertEquals(expected_message, response);

        /* Testar interpretar resposta*/
        Response received_response = Response.parse(response);

        assert(received_response instanceof LoginResponse);
        assertEquals(result, ((LoginResponse) received_response).getResult());
        assertEquals(certeficate, ((LoginResponse) received_response).getCertificate());

    }
}
