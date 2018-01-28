package com.ody.p2p.search.searchkey;

/**
 * 输入框里输入内容弹出的recyclerView里的tag对应的adapter
 */
public class SearchListTagAdapter  {//extends BaseAdapter

//    private final List<SearchKeywordListBean.WareList.Tag> mDataList;
//
//    public SearchListTagAdapter() {
//        mDataList = new ArrayList<>();
//    }
//
//    public void setDatas(List<SearchKeywordListBean.WareList.Tag> datas) {
//        mDataList.addAll(datas);
//        notifyDataSetChanged();
//    }
//
//    public void clearAndAddAll(List<SearchKeywordListBean.WareList.Tag> datas) {
//        mDataList.clear();
//        setDatas(datas);
//    }
//
//    public List<SearchKeywordListBean.WareList.Tag> getDataList(){
//        return  mDataList;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder holder;
//        if (convertView == null) {
//            holder = new ViewHolder();
//            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_keyword_list_tag, null);
//            holder.tv_tag = (TextView) convertView.findViewById(R.id.tv_tag);
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//
//        holder.tv_tag.setText(mDataList.get(position).tagshowquery);
//        return convertView;
//    }
//
//    @Override
//    public int getCount() {
//        return mDataList == null ? 0 : mDataList.size();
//    }
//
//    @Override
//    public SearchKeywordListBean.WareList.Tag getItem(int position) {
//        return mDataList.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    public class ViewHolder {
//        private TextView tv_tag;
//    }
}
