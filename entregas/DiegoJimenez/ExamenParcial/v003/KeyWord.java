package v003;

public class KeyWord {
    private int id;
    private String name;

    public KeyWord(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString() {
        return "> KeyWord{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}'; 
    }
}

    

   