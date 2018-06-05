package eyecare.visint.pac.pac;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/*
 *********Created by Akshath K, Punith B, Ajay J*********
*/
public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder>
{
    private ArrayList<User> mDataSet;

    public UsersAdapter(ArrayList<User> mDataSet)
    {
        this.mDataSet = mDataSet;
    }

    @Override
    public int getItemCount()
    {
        return mDataSet.size();
    }

    /**
     * Filter Logic
     **/
    public void animateTo(List<User> models)
    {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<User> newModels)
    {
        for (int i = mDataSet.size() - 1; i >= 0; i--)
        {
            final User model = mDataSet.get(i);
            if (!newModels.contains(model))
            {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<User> newModels)
    {
        for (int i = 0, count = newModels.size(); i < count; i++)
        {
            final User model = newModels.get(i);
            if (!mDataSet.contains(model))
            {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<User> newModels)
    {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--)
        {
            final User model = newModels.get(toPosition);
            final int fromPosition = mDataSet.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition)
            {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public User removeItem(int position)
    {
        final User model = mDataSet.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, User model)
    {
        mDataSet.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition)
    {
        final User model = mDataSet.remove(fromPosition);
        mDataSet.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_patientslist, parent, false);
        UserViewHolder userViewHolder = new UserViewHolder(v);
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position)
    {
        holder.id_entry.setText(mDataSet.get(position).getId());
        holder.fname_entry.setText(mDataSet.get(position).getFname());
        holder.lname_entry.setText(mDataSet.get(position).getLname());
        holder.icon_entry.setText("" + mDataSet.get(position).getFname().charAt(0));
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardView;
        TextView id_entry, fname_entry, lname_entry, icon_entry;

        UserViewHolder(View itemView)
        {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.user_layout);
            id_entry = (TextView) itemView.findViewById(R.id.id_entry);
            fname_entry = (TextView) itemView.findViewById(R.id.fname_entry);
            lname_entry = (TextView) itemView.findViewById(R.id.lname_entry);
            icon_entry = (TextView) itemView.findViewById(R.id.icon_entry);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }
}