package com.ryan.simsmmtrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startBtn = findViewById(R.id.startBtn);
        startBtn.setOnClickListener(view -> {
            generateSolution();
        });
    }

    private void generateSolution()
    {
        // TODO: Stop and drop warning if some of buttons not selected (use RadioGroup)
        ArrayList<RadioButton> heroGrp = new ArrayList<>();
        heroGrp.add(findViewById(R.id.heroBtnBlue));
        heroGrp.add(findViewById(R.id.heroBtnRed));
        heroGrp.add(findViewById(R.id.heroBtnBlack));
        heroGrp.add(findViewById(R.id.heroBtnYellow));
        heroGrp.add(findViewById(R.id.heroBtnWhite));
        int heroExclude = 0;
        for (int i = 0; i < heroGrp.size(); ++i)
        {
            if (heroGrp.get(i).isChecked())
            {
                heroExclude = i;
                break;
            }
        }

        ArrayList<RadioButton> enemyGrp = new ArrayList<>();
        enemyGrp.add(findViewById(R.id.enemyBtnBlue));
        enemyGrp.add(findViewById(R.id.enemyBtnRed));
        enemyGrp.add(findViewById(R.id.enemyBtnBlack));
        enemyGrp.add(findViewById(R.id.enemyBtnYellow));
        enemyGrp.add(findViewById(R.id.enemyBtnWhite));
        int enemyExclude = 0;
        for (int i = 0; i < enemyGrp.size(); ++i)
        {
            if (enemyGrp.get(i).isChecked())
            {
                enemyExclude = i;
                break;
            }
        }

        ArrayList<Spell> spells = new ArrayList<>();
        spells.add(
                new Spell("Синий торнадо", 2, 3, R.drawable.blue_tornado)
        );
        spells.add(
                new Spell("Красная волна", 0, 2, R.drawable.red_wave )
        );
        spells.add(
                new Spell("Черная буря", 3, 4, R.drawable.black_storm)
        );
        spells.add(
                new Spell("Желтая сера", 1, 4, R.drawable.yellow_sulfur)
        );
        spells.add(
                new Spell("Белая молния", 0, 1, R.drawable.white_lighting)
        );

        ArrayList<ImageView> spellImages = new ArrayList<>();
        spellImages.add(findViewById(R.id.fSpell));
        spellImages.add(findViewById(R.id.sSpell));
        spellImages.add(findViewById(R.id.tSpell));
        spellImages.add(findViewById(R.id.frSpell));

        // TODO: Lines 50-54 and 66-80 are fully duplicates
        int contraNum  = spells.get(heroExclude).getFirstContra();
        if (contraNum != enemyExclude)
        {
            for (int i = 0; i < spells.size(); ++i)
            {
                if (i == heroExclude || i == contraNum)
                    continue;

                Spell spell = spells.get(i);
                if (spell.getFirstContra() == contraNum || spell.getSecondContra() == contraNum)
                {
                    spell.define(contraNum);
                }
            }
        }

        contraNum  = spells.get(heroExclude).getSecondContra();
        if (contraNum != enemyExclude)
        {
            for (int i = 0; i < spells.size(); ++i)
            {
                if (i == heroExclude || i == contraNum)
                    continue;

                Spell spell = spells.get(i);
                if (spell.getFirstContra() == contraNum || spell.getSecondContra() == contraNum)
                {
                    spell.define(contraNum);
                }
            }
        }

        // TODO: Next three loops have very similar if clause
        for (int i = 0; i < spells.size(); ++i)
        {
            if (i == enemyExclude || i == heroExclude || spells.get(i).isDefined())
                continue;

            Spell spell = spells.get(i);
            if (spell.getFirstContra() == enemyExclude)
                spell.define(spell.getSecondContra());
            else if (spell.getSecondContra() == enemyExclude)
                spell.define(spell.getFirstContra());
        }

        // TODO: Next two loops are fully duplicates
        for (int i = 0; i < spells.size(); ++i)
        {
            if (i == heroExclude || spells.get(i).isDefined())
                continue;

            Spell spell = spells.get(i);
            for (int j = 0; j < spells.size(); ++j)
            {
                if (j == heroExclude || i == j || !spells.get(j).isDefined())
                    continue;

                int defined = spells.get(j).getDefined();
                if (spell.getFirstContra() == defined)
                    spell.define(spell.getSecondContra());
                else if (spell.getSecondContra() == defined)
                    spell.define(spell.getFirstContra());
            }
        }

        for (int i = 0; i < spells.size(); ++i)
        {
            if (i == heroExclude || spells.get(i).isDefined())
                continue;

            Spell spell = spells.get(i);
            for (int j = 0; j < spells.size(); ++j)
            {
                if (j == heroExclude || i == j || !spells.get(j).isDefined())
                    continue;

                int defined = spells.get(j).getDefined();
                if (spell.getFirstContra() == defined)
                    spell.define(spell.getSecondContra());
                else if (spell.getSecondContra() == defined)
                    spell.define(spell.getFirstContra());
            }
        }

        TextView spellLbl = findViewById(R.id.spellLbl);
        int indicator = 0;
        for (int i = 0; i < spells.size(); ++i)
        {
            if (i == enemyExclude)
            {
                indicator = 1;
                continue;
            }

            ImageView image = spellImages.get(i - indicator);
            image.setImageResource(spells.get(i).getImageId());
            final int finalI = i;
            image.setOnClickListener(view -> {
                for (int j = 0; j < spells.size(); ++j) {
                    if (finalI == spells.get(j).getDefined())
                        spellLbl.setText(spells.get(j).getName());
                }
            });
        }
    }
}