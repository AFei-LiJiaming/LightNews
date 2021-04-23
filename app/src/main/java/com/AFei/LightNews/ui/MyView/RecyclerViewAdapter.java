package com.AFei.LightNews.ui.MyView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.Blinger.base.utils.LogUtils;
import com.AFei.LightNews.R;
import com.AFei.LightNews.config.Constant;

import java.util.List;



public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private Context context;
        private List<Integer> headImgTypeList;
        private List<String> userNameList;
        private List<String> timeList;
        private List<Integer> acclaimNumList;
        private List<String> reviewContentList;
        private List<Integer> statusList;
    private List<String> commentIdList;

    public RecyclerViewAdapter(Context context, List<String> userNameList, List<String> timeList, List<Integer> acclaimNumList, List<String> reviewContentList, List<Integer> statusList, List<Integer> headImgTypeList, List<String> commentIdList
                ) {
            this.context = context;

            this.headImgTypeList = headImgTypeList;
            ///LogUtils.d(Constant.debugName+"RecyclerViewAdapter",this.headImgTypeList.);
            this.userNameList = userNameList;
            this.timeList = timeList;
            this.acclaimNumList = acclaimNumList;
            this.reviewContentList = reviewContentList;
            this.statusList = statusList;
        this.commentIdList = commentIdList;

            LogUtils.d(Constant.debugName+"recycler"," into construct function of RecyclerViewAdapter");
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder holder = getViewHolderByViewType(viewType);

            return holder;
        }

        private RecyclerView.ViewHolder getViewHolderByViewType(int viewType) {
            View view_other = View.inflate(context, R.layout.item_review, null);

            RecyclerView.ViewHolder holder = null;
            holder = new ViewHolderOther(view_other);

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            //
        }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position,List payloads) {
        setOther(holder,position,payloads);
    }
        //填充内容
        private void setOther(@NonNull RecyclerView.ViewHolder holder, final int position,List payloads){
            if (payloads.isEmpty()){
                if (userNameList.get(position) != null){
                    ((ViewHolderOther)holder).userNameTx.setText(userNameList.get(position));
                }
                if (acclaimNumList.get(position) != null){
                    ((ViewHolderOther)holder).acclaimNumTv.setText(acclaimNumList.get(position).toString());
                }
                if (reviewContentList.get(position) != null){
                    ((ViewHolderOther)holder).reviewContentTv.setText(reviewContentList.get(position));
                }
                if (timeList.get(position) != null){
                    ((ViewHolderOther)holder).timeTv.setText(timeList.get(position));
                }
                if (statusList.get(position) != null){
                    ((ViewHolderOther)holder).acclaimImg.setImageResource((statusList.get(position)) != 0 ? R.drawable.zan_red : R.drawable.zan_grey);
                }
                if (headImgTypeList.get(position) != null){
                    int id = 0;
                    switch (headImgTypeList.get(position)){
                        case 1:
                            id = R.drawable.head_image1;
                            break;
                        case 2:
                            id = R.drawable.head_image2;
                            break;
                        case 3:
                            id = R.drawable.head_image3;
                            break;
                        case 4:
                            id = R.drawable.head_image4;
                            break;
                        case 5:
                            id = R.drawable.head_image5;
                            break;
                        case 6:
                            id = R.drawable.head_image6;
                            break;
                        case 7:
                            id = R.drawable.head_image7;
                            break;
                        default:
                            id = R.drawable.head_image6;
                            break;
                    }
                    ((ViewHolderOther)holder).circleImageView.setImageResource(id);
                }

            }else{
                int type= (int) payloads.get(0);
                switch (type){
                    case 0:
                        ((ViewHolderOther)holder).acclaimNumTv.setText(acclaimNumList.get(position).toString());
                        break;
                }
            }

            if (mOnItemClickListener !=null){
                ((ViewHolderOther)holder).acclaimImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnItemClickListener.onClick(view,position);
                    }
                });
            }
        }

    //新增评论
    public void addData(int position, String username, String date, int acclaimNum, String reviewContent, int status, int imageType, String commentId) {
        //mDatas.add(position, "Insert " + position);

        userNameList.add(position,username);
        timeList.add(position,date);
        acclaimNumList.add(position,acclaimNum);
        reviewContentList.add(position,reviewContent);
        statusList.add(position,status);
        headImgTypeList.add(position,imageType);
        commentIdList.add(position, commentId);

        LogUtils.d(Constant.debugName+"WebActivity list size",acclaimNumList.size()+"");

        notifyItemInserted(position);

    }

    public void addAcclaimNum(int position){
        acclaimNumList.set(position,acclaimNumList.get(position)+1);
        notifyItemChanged(position,0);
    }

    public void decideAcclaimNum(int position){
        acclaimNumList.set(position,acclaimNumList.get(position)-1);
        notifyItemChanged(position,0);
    }

        @Override
        public int getItemCount() {
            return reviewContentList.size();
        }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onClick(View view,int position);
    }

    class ViewHolderOther extends RecyclerView.ViewHolder{
            public TextView userNameTx,acclaimNumTv,reviewContentTv,timeTv;
            public com.Blinger.base.utils.CircleImageView circleImageView;
            public ImageView acclaimImg;

            public ViewHolderOther(View itemView) {
                super(itemView);

                reviewContentTv = (TextView) itemView.findViewById(R.id.item_other_content_tx);
                userNameTx = (TextView) itemView.findViewById(R.id.item_other_username_tx);
                circleImageView = (com.Blinger.base.utils.CircleImageView) itemView.findViewById(R.id.item_other_head_img);
                acclaimNumTv = (TextView) itemView.findViewById(R.id.item_other_acclaim_num_tv);
                timeTv = (TextView) itemView.findViewById(R.id.item_other_time_tv);
                acclaimImg = (ImageView) itemView.findViewById(R.id.item_other_review_acclaim_icon_iv);
            }
        }
}