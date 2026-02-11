package dr.inf.drin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

public class Game extends AppCompatActivity {

    Dialog dialog;

    public int pers;
    public int dr = 4;
    public int hu = 4;
    public int sw = 4;
    public int mo = 4;
    public int hod = 0;
    public int[] pred = {-1, -1, -1, -1, -1, -1, -1};
    public int event = -1;

    Random random = new Random();
    final Pers Pers = new Pers();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        final ImageView imgPers = findViewById(R.id.img_pers);

        final ImageView fire = findViewById(R.id.fire);

        final ImageView human = findViewById(R.id.human);

        final ImageView sword = findViewById(R.id.sword);

        final ImageView money = findViewById(R.id.money);

        final TextView mainText = findViewById(R.id.main_text);

        final TextView btnleft = findViewById(R.id.lbtn);

        final TextView btnright = findViewById(R.id.rbtn);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        pers =  random.nextInt( 20 );
        imgPers.setImageResource(Pers.pers[0]);
        mainText.setText(Pers.texts[pers]);
        btnleft.setText(Pers.lbtn[pers]);
        btnright.setText(Pers.rbtn[pers]);

        dialog = new Dialog(this);
        btnleft.setOnTouchListener((view, motionEvent) -> {

            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                btnright.setEnabled(false);
            }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                hod++;
                btnright.setEnabled(true);

                dr += Pers.dra1[pers];
                if (dr < 0) dr = 0;
                if (dr > 8) dr = 8;
                hu += Pers.hum1[pers];
                if (hu < 0) hu = 0;
                if (hu > 8) hu = 8;
                sw += Pers.swo1[pers];
                if (sw < 0) sw = 0;
                if (sw > 8) sw = 8;
                mo += Pers.mon1[pers];
                if (mo < 0) mo = 0;
                if (mo > 8) mo = 8;
                if (dr == 0 || dr == 8 || hu == 0 || hu == 8 || sw ==0 || sw == 8 || mo == 0 || mo ==8) {
                    SharedPreferences save = getSharedPreferences("Save", MODE_PRIVATE);
                    final int record = save.getInt("Record", 0);
                    if (record < hod) {
                        SharedPreferences.Editor editor = save.edit();
                        editor.putInt("Record", hod);
                        editor.commit();
                    }
                    fire.setVisibility(fire.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                    human.setVisibility(human.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                    sword.setVisibility(sword.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                    money.setVisibility(money.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                    btnleft.setVisibility(btnright.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                    mainText.setVisibility(mainText.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                    btnright.setVisibility(btnright.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                    imgPers.setVisibility(imgPers.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.lose);
                    dialog.setCancelable(false);
                    TextView losetext = dialog.findViewById(R.id.losetext);
                    ImageView loseimage = dialog.findViewById(R.id.loseimg);
                    TextView hods = dialog.findViewById(R.id.hods);
                    hods.setText("Сделано ходов:" + Integer.toString(hod));
                    TextView rhods = dialog.findViewById(R.id.rhods);
                    rhods.setText("Рекорд ходов:" + Integer.toString(record));
                    if (dr == 0) {
                        losetext.setText(R.string.losedr0);
                        loseimage.setImageResource(R.drawable.fire0);
                    }
                    if (dr == 8) {
                        losetext.setText(R.string.losedr8);
                        loseimage.setImageResource(R.drawable.fire8);
                    }
                    if (hu == 0) {
                        losetext.setText(R.string.losehu0);
                        loseimage.setImageResource(R.drawable.human0);
                    }
                    if (hu == 8) {
                        losetext.setText(R.string.losehu8);
                        loseimage.setImageResource(R.drawable.human8);
                    }
                    if (sw == 0) {
                        losetext.setText(R.string.losesw0);
                        loseimage.setImageResource(R.drawable.sword0);
                    }
                    if (sw == 8) {
                        losetext.setText(R.string.losesw8);
                        loseimage.setImageResource(R.drawable.sword8);
                    }
                    if (mo == 0) {
                        losetext.setText(R.string.losemo0);
                        loseimage.setImageResource(R.drawable.money0);
                    }
                    if (mo == 8) {
                        losetext.setText(R.string.losemo8);
                        loseimage.setImageResource(R.drawable.money8);
                    }
                    TextView backbtn = dialog.findViewById(R.id.backbtn);

                    backbtn.setOnTouchListener((view1, motionEvent1) -> {
                        Intent intent = new Intent(Game.this, MainActivity.class);
                        startActivity(intent); finish();
                        dialog.dismiss();
                        return false;
                    });
                    dialog.show();
                }
                pred[6] = pred[5];
                pred[5] = pred[4];
                pred[4] = pred[3];
                pred[3] = pred[2];
                pred[2] = pred[1];
                pred[1] = pred[0];
                pred[0] = pers;
                fire.setImageResource(Pers.fire[dr]);
                human.setImageResource(Pers.human[hu]);
                sword.setImageResource(Pers.sword[sw]);
                money.setImageResource(Pers.money[mo]);
                while (pers == pred[0] || pers == pred[1] || pers == pred[2] || pers == pred[3] || pers == pred[4] || pers == pred[5] || pers == pred[6])
                    pers =  random.nextInt( 20 );
                imgPers.setImageResource(Pers.pers[pers]);
                mainText.setText(Pers.texts[pers]);
                btnleft.setText(Pers.lbtn[pers]);
                btnright.setText(Pers.rbtn[pers]);
                if (event >= random.nextInt(4) + 3){
                    pers = 20;
                    event = -1;
                    imgPers.setImageResource(R.drawable.lady);
                    mainText.setText(R.string.event1);
                    btnleft.setText(R.string.eventl1);
                    btnright.setText(R.string.eventr1);
                }
                if (event > -1) {
                    event++;
                }
            }
            return false;
        });

        btnright.setOnTouchListener((view, motionEvent) -> {

            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                btnleft.setEnabled(false);
            }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                hod++;
                btnleft.setEnabled(true);

                dr += Pers.dra2[pers];
                if (dr < 0) dr = 0;
                if (dr > 8) dr = 8;
                hu += Pers.hum2[pers];
                if (hu < 0) hu = 0;
                if (hu > 8) hu = 8;
                sw += Pers.swo2[pers];
                if (sw < 0) sw = 0;
                if (sw > 8) sw = 8;
                mo += Pers.mon2[pers];
                if (mo < 0) mo = 0;
                if (mo > 8) mo = 8;
                if (dr == 0 || dr == 8 || hu == 0 || hu == 8 || sw ==0 || sw == 8 || mo == 0 || mo ==8) {
                    SharedPreferences save = getSharedPreferences("Save", MODE_PRIVATE);
                    final int record = save.getInt("Record", 0);
                    if (record < hod) {
                        SharedPreferences.Editor editor = save.edit();
                        editor.putInt("Record", hod);
                        editor.commit();
                    }
                    fire.setVisibility(fire.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                    human.setVisibility(human.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                    sword.setVisibility(sword.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                    money.setVisibility(money.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                    btnleft.setVisibility(btnright.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                    mainText.setVisibility(mainText.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                    btnright.setVisibility(btnright.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                    imgPers.setVisibility(imgPers.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.lose);
                    dialog.setCancelable(false);
                    TextView losetext = dialog.findViewById(R.id.losetext);
                    ImageView loseimage = dialog.findViewById(R.id.loseimg);
                    TextView hods = dialog.findViewById(R.id.hods);
                    hods.setText("Сделано ходов:" + Integer.toString(hod));
                    TextView rhods = dialog.findViewById(R.id.rhods);
                    rhods.setText("Рекорд ходов:" + Integer.toString(record));
                    if (dr == 0) {
                        losetext.setText(R.string.losedr0);
                        loseimage.setImageResource(R.drawable.fire0);
                    }
                    if (dr == 8) {
                        losetext.setText(R.string.losedr8);
                        loseimage.setImageResource(R.drawable.fire8);
                    }
                    if (hu == 0) {
                        losetext.setText(R.string.losehu0);
                        loseimage.setImageResource(R.drawable.human0);
                    }
                    if (hu == 8) {
                        losetext.setText(R.string.losehu8);
                        loseimage.setImageResource(R.drawable.human8);
                    }
                    if (sw == 0) {
                        losetext.setText(R.string.losesw0);
                        loseimage.setImageResource(R.drawable.sword0);
                    }
                    if (sw == 8) {
                        losetext.setText(R.string.losesw8);
                        loseimage.setImageResource(R.drawable.sword8);
                    }
                    if (mo == 0) {
                        losetext.setText(R.string.losemo0);
                        loseimage.setImageResource(R.drawable.money0);
                    }
                    if (mo == 8) {
                        losetext.setText(R.string.losemo8);
                        loseimage.setImageResource(R.drawable.money8);
                    }
                    TextView backbtn = dialog.findViewById(R.id.backbtn);

                    backbtn.setOnTouchListener((view1, motionEvent1) -> {
                        Intent intent = new Intent(Game.this, MainActivity.class);
                        startActivity(intent); finish();
                        dialog.dismiss();
                        return false;
                    });
                    dialog.show();
                }
                pred[6] = pred[5];
                pred[5] = pred[4];
                pred[4] = pred[3];
                pred[3] = pred[2];
                pred[2] = pred[1];
                pred[1] = pred[0];
                pred[0] = pers;
                while (pers == pred[0] || pers == pred[1] || pers == pred[2] || pers == pred[3] || pers == pred[4] || pers == pred[5] || pers == pred[6])
                    pers =  random.nextInt( 20 );
                fire.setImageResource(Pers.fire[dr]);
                human.setImageResource(Pers.human[hu]);
                sword.setImageResource(Pers.sword[sw]);
                money.setImageResource(Pers.money[mo]);
                imgPers.setImageResource(Pers.pers[pers]);
                mainText.setText(Pers.texts[pers]);
                btnleft.setText(Pers.lbtn[pers]);
                btnright.setText(Pers.rbtn[pers]);
                if (event >= random.nextInt(2) + 3){
                    pers = 20;
                    event = -1;
                    imgPers.setImageResource(R.drawable.lady);
                    mainText.setText(R.string.event1);
                    btnleft.setText(R.string.eventl1);
                    btnright.setText(R.string.eventr1);
                }
                if (event > -1) {
                    event++;
                }
                if (pers == 4) {
                    event = 0;
                }
            }
            return false;
        });

    }
    @Override
    public void onBackPressed(){
        try{
            Intent intent = new Intent(Game.this, MainActivity.class);
            startActivity(intent); finish();
        }catch (Exception e){
            //
        }
    }
}