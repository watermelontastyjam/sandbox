package ru.ssau.pigeonmail.message;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.mannan.translateapi.Language;
import com.mannan.translateapi.TranslateAPI;

import java.util.List;
import java.util.Locale;

import ru.ssau.pigeonmail.R;
import ru.ssau.pigeonmail.utils.ChatUtil;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>{
    private List<Message> messages;
    private String langOfCurrUser;

    public MessagesAdapter(List<Message> messages){
        this.messages = messages;

    }
    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(viewType,parent,false);
        langOfCurrUser =  Locale.getDefault().getLanguage();
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messages.get(position);
        String autodetectedLang = Language.AUTO_DETECT;
        if(!autodetectedLang.equals(langOfCurrUser) ) {
            TranslateAPI translateAPI = new TranslateAPI(
                    Language.AUTO_DETECT,   //Source Language
                    langOfCurrUser,         //Target Language
                    message.getText());           //Query Text

            translateAPI.setTranslateListener(new TranslateAPI.TranslateListener() {
                @Override
                public void onSuccess(String translatedText) {
                    holder.messageTv.setText(translatedText);
                }

                @Override
                public void onFailure(String ErrorText) {
                    holder.messageTv.setText(message.getText());
                    Log.d("ERROR", "onFailure: " + ErrorText);
                }
            });
        }
        else {
            holder.messageTv.setText(message.getText());
        }
        holder.dateTv.setText(message.getDate());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(messages.get(position).getOwnerId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
            return R.layout.message_from_curr_user_rv_item;
        else return R.layout.message_rv_item;
    }

    class MessageViewHolder extends RecyclerView.ViewHolder{

        TextView messageTv, dateTv;
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTv = itemView.findViewById(R.id.message_tv);
            dateTv = itemView.findViewById(R.id.message_date_tv);
        }
    }
}
