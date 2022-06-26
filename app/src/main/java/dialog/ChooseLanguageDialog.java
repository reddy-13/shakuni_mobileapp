package dialog;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.nexzenstudent.MainActivity;
import com.nexzenstudent.R;

import util.LanguagePrefs;

/**
 * Created by Rajesh on 2018-03-31.
 */

public class ChooseLanguageDialog extends AlertDialog implements View.OnClickListener {

    private Context context;

    public ChooseLanguageDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        setCanceledOnTouchOutside(false);
        // ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_choose_language, null);
        this.setView(dialogView);

        try {
            getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        } catch (Exception ex) {

        }

        Button btnEnglish = (Button) dialogView.findViewById(R.id.btnEnglish);
        Button btnArabic = (Button) dialogView.findViewById(R.id.btnArabic);

        btnEnglish.setOnClickListener(this);
        btnArabic.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        LanguagePrefs languagePrefs = new LanguagePrefs(context);

        switch (view.getId()) {
            case R.id.btnEnglish:
                languagePrefs.saveLanguage("en");
                languagePrefs.initRTL("en");
                dismiss();

                ((MainActivity) context).finish();
                context.startActivity(((MainActivity) context).getIntent());

                break;
            case R.id.btnArabic:
                languagePrefs.saveLanguage("ar");
                languagePrefs.initRTL("ar");
                dismiss();

                ((MainActivity) context).finish();
                context.startActivity(((MainActivity) context).getIntent());

                break;
        }
    }

}