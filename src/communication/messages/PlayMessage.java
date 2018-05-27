package communication.messages;

public class PlayMessage extends Message {

    private String name, country, capital, brand, animal, tvShow;


    public PlayMessage(String name, String country, String capital, String brand, String animal, String tvShow) {
        this.name = name;
        this.country = country;
        this.capital = capital;
        this.brand = brand;
        this.animal = animal;
        this.tvShow = tvShow;
        this.type = PLAY;
    }

    public PlayMessage(String[] args){

        if(args.length != 7){
            this.type = ERROR;
            return;
        }
        name = args[1];
        country = args[2];
        capital = args[3];
        brand = args[4];
        animal = args[5];
        tvShow = args[6];

    }

    @Override
    public String toString() {
        return PLAY + SEPARATOR + name + SEPARATOR + country +
                SEPARATOR + capital + SEPARATOR + brand + SEPARATOR + animal + SEPARATOR + tvShow;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getCapital() {
        return capital;
    }

    public String getBrand() {
        return brand;
    }

    public String getAnimal() {
        return animal;
    }

    public String getTvShow() {
        return tvShow;
    }
}
