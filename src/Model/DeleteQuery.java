package Model;

import java.util.Random;

public class DeleteQuery implements IQuery {
    public String tableName;
    public Condition condition;

    public DeleteQuery(String tableName, Condition condition){
        this.tableName = tableName;
        this.condition = condition;
    }

    @Override
    public Result ExecuteQuery() {
        /*TODO : replace with actual logic*/
        Random random = new Random();
        Result result = new Result(random.nextInt(50));
        return result;
    }

    @Override
    public boolean ValidateQuery() {
        /*TODO : replace with actual logic*/
        return true;
    }
}