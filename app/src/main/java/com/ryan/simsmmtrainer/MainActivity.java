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

        m_spells = new ArrayList<>();

        Button startBtn = findViewById(R.id.startBtn);
        startBtn.setOnClickListener(view -> {
            generateSolution();
        });
    }

    private void firstRun(int contraNum)
    {
        if (contraNum != m_enemyExclude)
        {
            for (int i = 0; i < m_spells.size(); ++i)
            {
                if (i == m_heroExclude || i == contraNum)
                    continue;

                Spell spell = m_spells.get(i);
                if (spell.getFirstContra() == contraNum || spell.getSecondContra() == contraNum)
                {
                    spell.define(contraNum);
                }
            }
        }
    }

    private void secondRun()
    {
        for (int i = 0; i < m_spells.size(); ++i)
        {
            if (i == m_heroExclude || m_spells.get(i).isDefined())
                continue;

            Spell spell = m_spells.get(i);
            for (int j = 0; j < m_spells.size(); ++j)
            {
                if (j == m_heroExclude || i == j || !m_spells.get(j).isDefined())
                    continue;

                int defined = m_spells.get(j).getDefined();
                if (spell.getFirstContra() == defined)
                    spell.define(spell.getSecondContra());
                else if (spell.getSecondContra() == defined)
                    spell.define(spell.getFirstContra());
            }
        }
    }

    private void generateSolution()
    {
        m_spells.clear();

        // TODO: Stop and drop warning if some of buttons not selected (use RadioGroup)
        ArrayList<RadioButton> heroGrp = new ArrayList<>();
        heroGrp.add(findViewById(R.id.heroBtnBlue));
        heroGrp.add(findViewById(R.id.heroBtnRed));
        heroGrp.add(findViewById(R.id.heroBtnBlack));
        heroGrp.add(findViewById(R.id.heroBtnYellow));
        heroGrp.add(findViewById(R.id.heroBtnWhite));
        for (int i = 0; i < heroGrp.size(); ++i)
        {
            if (heroGrp.get(i).isChecked())
            {
                m_heroExclude = i;
                break;
            }
        }

        ArrayList<RadioButton> enemyGrp = new ArrayList<>();
        enemyGrp.add(findViewById(R.id.enemyBtnBlue));
        enemyGrp.add(findViewById(R.id.enemyBtnRed));
        enemyGrp.add(findViewById(R.id.enemyBtnBlack));
        enemyGrp.add(findViewById(R.id.enemyBtnYellow));
        enemyGrp.add(findViewById(R.id.enemyBtnWhite));
        for (int i = 0; i < enemyGrp.size(); ++i)
        {
            if (enemyGrp.get(i).isChecked())
            {
                m_enemyExclude = i;
                break;
            }
        }

        // TODO: Add at least Russian and English version
        m_spells.add(
                new Spell("Синий торнадо", 2, 3, R.drawable.blue_tornado)
        );
        m_spells.add(
                new Spell("Красная волна", 0, 2, R.drawable.red_wave )
        );
        m_spells.add(
                new Spell("Черная буря", 3, 4, R.drawable.black_storm)
        );
        m_spells.add(
                new Spell("Желтая сера", 1, 4, R.drawable.yellow_sulfur)
        );
        m_spells.add(
                new Spell("Белая молния", 0, 1, R.drawable.white_lighting)
        );

        ArrayList<ImageView> spellImages = new ArrayList<>();
        spellImages.add(findViewById(R.id.fSpell));
        spellImages.add(findViewById(R.id.sSpell));
        spellImages.add(findViewById(R.id.tSpell));
        spellImages.add(findViewById(R.id.frSpell));

        firstRun(m_spells.get(m_heroExclude).getFirstContra());

        firstRun(m_spells.get(m_heroExclude).getSecondContra());

        for (int i = 0; i < m_spells.size(); ++i)
        {
            if (i == m_enemyExclude || i == m_heroExclude || m_spells.get(i).isDefined())
                continue;

            Spell spell = m_spells.get(i);
            if (spell.getFirstContra() == m_enemyExclude)
                spell.define(spell.getSecondContra());
            else if (spell.getSecondContra() == m_enemyExclude)
                spell.define(spell.getFirstContra());
        }

        secondRun();
        secondRun();

        TextView spellLbl = findViewById(R.id.spellLbl);
        int indicator = 0;
        for (int i = 0; i < m_spells.size(); ++i)
        {
            if (i == m_enemyExclude)
            {
                indicator = 1;
                continue;
            }

            ImageView image = spellImages.get(i - indicator);
            image.setImageResource(m_spells.get(i).getImageId());
            final int finalI = i;
            image.setOnClickListener(view -> {
                for (int j = 0; j < m_spells.size(); ++j) {
                    if (finalI == m_spells.get(j).getDefined())
                        spellLbl.setText(m_spells.get(j).getName());
                }
            });
        }
    }

    private ArrayList<Spell> m_spells;

    private int m_heroExclude;
    private int m_enemyExclude;
}