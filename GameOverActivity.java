package zuzidev.skyflying;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameOverActivity extends AppCompatActivity {
    Button newGameButton;
    Button exitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_game_over );

        newGameButton = (Button) findViewById(R.id.newGameButton);
        newGameButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent( getApplicationContext(),MainActivity.class ) );
            }
        } );

        exitButton = (Button) findViewById( R.id.exitButton );
        exitButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit( 0 );
            }
        } );
    }
}
