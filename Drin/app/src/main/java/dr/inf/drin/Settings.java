package dr.inf.drin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Button buttonBack = (Button)findViewById(R.id.back_btn);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent intent = new Intent(Settings.this, MainActivity.class);
                    startActivity(intent); finish();
                }catch (Exception e){
                    //
                }
            }
        });
    }
    @Override
    public void onBackPressed(){
        try{
            Intent intent = new Intent(Settings.this, MainActivity.class);
            startActivity(intent); finish();
        }catch (Exception e){
            //
        }
    }
}