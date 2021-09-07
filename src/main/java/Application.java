import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import processor.CoffeeMachine;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Slf4j
public class Application {

    //This runs a sample test case - provide the file name as argument e.g. sample.json
    public static void main(String[] args) throws IOException {
        if(args.length < 1) {
            log.error("Input File Name Arg Missing!");
            return;
        }
        File inputFile = new File(Objects.requireNonNull(CoffeeMachine.class
                        .getClassLoader().getResource(args[0])).getFile());
        String json = FileUtils.readFileToString(inputFile, "UTF-8");
        CoffeeMachine coffeeMachine = CoffeeMachine.getInstance(json);
        coffeeMachine.process();
    }
}
