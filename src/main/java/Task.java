package ip.src.main.java;

public class Task{
    protected String description;
    protected int status;

    public Task(String description){
        this.description = description;
        this.status = 0;
    }

    public void setStatus(int status){
        this.status = status;
    }

    public String getStatus(){
        return (this.status == 1 ? "X" : " ");
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return "[" + getStatus() + "] " + description;
    }
}
