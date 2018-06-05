package eyecare.visint.pac.pac;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/*
 *********Created by Akshath K, Punith B, Ajay J*********
*/
public class RecyclerViewHolders_Home extends RecyclerView.ViewHolder implements View.OnClickListener
{
    Flag obj = new Flag();
    Context context1, context2, context3;
    public TextView countryName;

    public RecyclerViewHolders_Home(View itemView)
    {
        super(itemView);
        itemView.setOnClickListener(this);
        countryName = (TextView) itemView.findViewById(R.id.country_name);
    }

    @Override
    public void onClick(View view)
    {
        context1 = itemView.getContext();
        context2 = itemView.getContext();
        context3 = itemView.getContext();
        int a = getPosition();
        switch (a)
        {
            case 0:
                Intent intent1 = new Intent(context1, ListPatientData.class);
                context1.startActivity(intent1);
                break;
            case 1:
                int k = obj.returnflag();
                //String s=Integer.toString(k);
                //Toast.makeText(view.getContext(),s, Toast.LENGTH_SHORT).show();
                if (k == 0)
                {
                    Intent intent2 = new Intent(context2, Search_Patient.class);
                    context2.startActivity(intent2);
                } else
                {
                    Intent intent3 = new Intent(context3, Test.class);
                    context3.startActivity(intent3);
                }
                break;
        }
    }
}