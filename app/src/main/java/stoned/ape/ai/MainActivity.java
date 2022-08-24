package stoned.ape.ai;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.os.Bundle;
import java.util.*;
import java.util.function.Supplier;
import java.util.ArrayList;
import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.engine.Engine;
import com.theokanning.openai.completion.CompletionChoice;

public class MainActivity extends AppCompatActivity {
    TextView search, resultTextView;
    ImageButton generateButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search = findViewById(R.id.search);
        resultTextView = findViewById(R.id.resultTextView);
        generateButton = findViewById(R.id.generateButton);


        generateButton.setOnClickListener(this::onClick);
    }

    private void onClick(View view) {
        String searchText = search.getText().toString();
        String token = System.getenv("OPENAI_TOKEN");
        OpenAiService service = new OpenAiService(token);
        Engine davinci = service.getEngine("davinci");
        ArrayList<CompletionChoice> storyArray = new ArrayList<CompletionChoice>();
        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt(searchText)
                .temperature(0.7)
                .maxTokens(96)
                .topP(1.0)
                .frequencyPenalty(0.0)
                .presencePenalty(0.3)
                .echo(true)     // echoes back the prompt in addition to tokens
                .build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            service.createCompletion("davinci", completionRequest).getChoices().forEach(line -> {
                storyArray.add(line);
            });
        }
        String result = "";
        for (CompletionChoice line : storyArray) {
            result += line.getText() + "\n";
        }

        resultTextView.setText(storyArray.get(0).getText());



    }
}






















