package communication.messages;

public class LetterMessage extends Message {

    private char letter;

    public LetterMessage(char letter){
        this.letter = letter;
        this.type = LETTER;
    }

    public LetterMessage(String[] args){

        if(args.length != 2){
            this.type = ERROR;
            return;
        }

        if(args[1] == ""){
            this.type = ERROR;
            return;
        }

        letter = args[1].substring(0,1).charAt(0);
    }

    @Override
    public String toString() {
        return LOGIN + SEPARATOR + letter;
    }

    public char getLetter() {
        return letter;
    }


}
