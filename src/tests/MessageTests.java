package tests;

import communication.Header;
import communication.messages.*;
import communication.responses.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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

        /* Testar enviar resposta com campos vazios */
        String expected_response1 = Header.LOGIN + Header.SEPARATOR + "";
        String response1 = new LoginResponse("", "").toString();

        assertEquals(expected_response1, response1);

        /* Testar interpretar resposta com campos vazios */
        Response received_response1 = Response.parse(response1);

        assert(received_response1 instanceof LoginResponse);
        assertEquals(Header.ERROR, received_response1.getType());

        String result1 = Header.FAILURE;

        /* Testar criar resposta*/
        String expected_message2 = Header.LOGIN + Header.SEPARATOR + result1;
        String response2 = new LoginResponse(result1, "").toString();

        assertEquals(expected_message2, response2);

    }

    @Test
    public void test_register_messages(){

        String username = "David";
        String password = "abcd1234";

        /* Testar criar mensagem*/
        String expected_message = Header.REGISTER + Header.SEPARATOR + username + Header.SEPARATOR + password;
        String message = new RegisterMessage(username, password).toString();

        assertEquals(expected_message, message);

        /* Testar interpretar mensagem*/
        Message received_message = Message.parse(message);

        assert(received_message instanceof RegisterMessage);
        assertEquals(username, ((RegisterMessage) received_message).getUsername());
        assertEquals(password, ((RegisterMessage) received_message).getPassword());

        /* Testar enviar mensagem com campos vazios */
        String expected_message1 = Header.REGISTER + Header.SEPARATOR + "" + Header.SEPARATOR + "";
        String message1 = new RegisterMessage("", "").toString();

        assertEquals(expected_message1, message1);

        /* Testar interpretar mensagem com campos vazios */
        Message received_message1 = Message.parse(message1);

        assert(received_message1 instanceof RegisterMessage);
        assertEquals(Header.ERROR, received_message1.getType());

    }

    @Test
    public void test_register_responses(){

        String result = Header.SUCCESS;
        String certeficate = "afgh35g2jh52hjk23kh34663t";

        /* Testar criar resposta*/
        String expected_message = Header.REGISTER + Header.SEPARATOR + result + Header.SEPARATOR + certeficate;
        String response = new RegisterResponse(result, certeficate).toString();

        assertEquals(expected_message, response);

        /* Testar interpretar resposta*/
        Response received_response = Response.parse(response);

        assert(received_response instanceof RegisterResponse);
        assertEquals(result, ((RegisterResponse) received_response).getResult());
        assertEquals(certeficate, ((RegisterResponse) received_response).getCertificate());

        /* Testar enviar resposta com campos vazios */
        String expected_response1 = Header.REGISTER + Header.SEPARATOR + "";
        String response1 = new RegisterResponse("", "").toString();

        assertEquals(expected_response1, response1);

        /* Testar interpretar resposta com campos vazios */
        Response received_response1 = Response.parse(response1);

        assert(received_response1 instanceof RegisterResponse);
        assertEquals(Header.ERROR, received_response1.getType());

    }

    @Test
    public void test_list_rooms_messages(){

        /* Testar criar mensagem*/
        String expected_message = Header.LISTROOMS;
        String message = new ListRoomsMessage().toString();

        assertEquals(expected_message, message);

        /* Testar interpretar mensagem*/
        Message received_message = Message.parse(message);
        assert(received_message instanceof ListRoomsMessage);

    }

    @Test
    public void test_list_rooms_responses(){

        /* Testar criar reposta failure*/
        String expected_response = Header.LISTROOMS + Header.SEPARATOR + Header.FAILURE;
        String response = new ListRoomsResponse(Header.FAILURE, null).toString();

        assertEquals(expected_response, response);

        /* Testar interpretar resposta failure*/
        Response received_response = Response.parse(response);
        assert(received_response instanceof ListRoomsResponse);
        assertEquals(Header.FAILURE, ((ListRoomsResponse) received_response).getResult());

        /* Testar criar reposta sucess*/
        String expected_response1 = Header.LISTROOMS;
        ArrayList<String> rooms = new ArrayList<String>();
        for(int i=0; i<5; i++){
            expected_response1 += Header.SEPARATOR + "room " + i;
            rooms.add("room " + i);
        }

        String response1 = new ListRoomsResponse(Header.SUCCESS, rooms).toString();

        assertEquals(expected_response1, response1);

        /* Testar interpretar resposta sucess*/
        Response received_response1 = Response.parse(response1);
        assert(received_response1 instanceof ListRoomsResponse);
        assertEquals(Header.SUCCESS, ((ListRoomsResponse) received_response1).getResult());
        assertEquals(5, ((ListRoomsResponse) received_response1).getRooms().size());

    }

    @Test
    public void test_join_room_messages(){

        String room_name = "4732647";

        /* Testar criar mensagem*/
        String expected_message = Header.JOINROOM + Header.SEPARATOR + room_name;
        String message = new JoinRoomMessage(room_name).toString();

        assertEquals(expected_message, message);

        /* Testar interpretar mensagem*/
        Message received_message = Message.parse(message);

        assert(received_message instanceof JoinRoomMessage);
        assertEquals(room_name, ((JoinRoomMessage) received_message).getRoomName());

        /* Testar enviar mensagem com campos vazios */
        String expected_message1 = Header.JOINROOM + Header.SEPARATOR + "";
        String message1 = new JoinRoomMessage("").toString();

        assertEquals(expected_message1, message1);

        /* Testar interpretar mensagem com campos vazios */
        Message received_message1 = Message.parse(message1);

        assert(received_message1 instanceof JoinRoomMessage);
        assertEquals(Header.ERROR, received_message1.getType());

    }

    @Test
    public void test_join_room_responses(){

        String result = Header.SUCCESS;

        /* Testar criar resposta*/
        String expected_message = Header.JOINROOM + Header.SEPARATOR + result;
        String response = new JoinRoomResponse(result).toString();

        assertEquals(expected_message, response);

        /* Testar interpretar resposta*/
        Response received_response = Response.parse(response);

        assert(received_response instanceof JoinRoomResponse);
        assertEquals(result, ((JoinRoomResponse) received_response).getResult());

        /* Testar enviar resposta com campos vazios */
        String expected_response1 = Header.JOINROOM + Header.SEPARATOR + "";
        String response1 = new JoinRoomResponse("").toString();

        assertEquals(expected_response1, response1);

        /* Testar interpretar resposta com campos vazios */
        Response received_response1 = Response.parse(response1);

        assert(received_response1 instanceof JoinRoomResponse);
        assertEquals(Header.ERROR, received_response1.getType());

        String result1 = Header.FAILURE;

        /* Testar criar resposta failure*/
        String expected_message2 = Header.JOINROOM + Header.SEPARATOR + result1;
        String response2 = new JoinRoomResponse(result1).toString();

        assertEquals(expected_message2, response2);

        /* Testar interpretar resposta failure */
        Response received_response2 = Response.parse(response2);

        assert(received_response2 instanceof JoinRoomResponse);
        assertEquals(Header.JOINROOM, received_response2.getType());
        assertEquals(result1, ((JoinRoomResponse) received_response2).getResult());

    }

    @Test
    public void test_create_room_messages(){

        String room_name = "4732647";

        /* Testar criar mensagem*/
        String expected_message = Header.CREATEROOM + Header.SEPARATOR + room_name;
        String message = new CreateRoomMessage(room_name).toString();

        assertEquals(expected_message, message);

        /* Testar interpretar mensagem*/
        Message received_message = Message.parse(message);

        assert(received_message instanceof CreateRoomMessage);
        assertEquals(room_name, ((CreateRoomMessage) received_message).getRoomName());

        /* Testar enviar mensagem com campos vazios */
        String expected_message1 = Header.CREATEROOM + Header.SEPARATOR + "";
        String message1 = new CreateRoomMessage("").toString();

        assertEquals(expected_message1, message1);

        /* Testar interpretar mensagem com campos vazios */
        Message received_message1 = Message.parse(message1);

        assert(received_message1 instanceof CreateRoomMessage);
        assertEquals(Header.ERROR, received_message1.getType());

    }

    @Test
    public void test_create_room_responses(){

        String result = Header.SUCCESS;

        /* Testar criar resposta*/
        String expected_message = Header.CREATEROOM + Header.SEPARATOR + result;
        String response = new CreateRoomResponse(result).toString();

        assertEquals(expected_message, response);

        /* Testar interpretar resposta*/
        Response received_response = Response.parse(response);

        assert(received_response instanceof CreateRoomResponse);
        assertEquals(result, ((CreateRoomResponse) received_response).getResult());

        /* Testar enviar resposta com campos vazios */
        String expected_response1 = Header.CREATEROOM + Header.SEPARATOR + "";
        String response1 = new CreateRoomResponse("").toString();

        assertEquals(expected_response1, response1);

        /* Testar interpretar resposta com campos vazios */
        Response received_response1 = Response.parse(response1);

        assert(received_response1 instanceof CreateRoomResponse);
        assertEquals(Header.ERROR, received_response1.getType());

        String result1 = Header.FAILURE;

        /* Testar criar resposta failure*/
        String expected_message2 = Header.CREATEROOM + Header.SEPARATOR + result1;
        String response2 = new CreateRoomResponse(result1).toString();

        assertEquals(expected_message2, response2);

        /* Testar interpretar resposta failure */
        Response received_response2 = Response.parse(response2);

        assert(received_response2 instanceof CreateRoomResponse);
        assertEquals(Header.CREATEROOM, received_response2.getType());
        assertEquals(result1, ((CreateRoomResponse) received_response2).getResult());

    }



}
