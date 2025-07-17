package com.example.quizletfake;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quizletfake.model.StudySession;
import com.example.quizletfake.model.StudySessionPrefs;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import android.graphics.Color;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class FragmentThongKe extends Fragment {
    private TextView tvTotalSession, tvTotalQuiz, tvStreak, tvTotalCorrect, tvTotalWrong, tvCorrectRate, tvAvgTime;
    private BarChart barChart;
    private RecyclerView rcvHistory;
    private HistoryAdapter historyAdapter;
    private List<StudySession> history;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_ke, container, false);
        tvTotalSession = view.findViewById(R.id.tvTotalSession);
        tvTotalQuiz = view.findViewById(R.id.tvTotalQuiz);
        tvStreak = view.findViewById(R.id.tvStreak);
        tvTotalCorrect = view.findViewById(R.id.tvTotalCorrect);
        tvTotalWrong = view.findViewById(R.id.tvTotalWrong);
        tvCorrectRate = view.findViewById(R.id.tvCorrectRate);
        tvAvgTime = view.findViewById(R.id.tvAvgTime);
        barChart = view.findViewById(R.id.barChart);
        rcvHistory = view.findViewById(R.id.rcvHistory);
        history = StudySessionPrefs.getHistory(requireContext());
        showStats();
        showChart();
        showHistory();
        return view;
    }

    private void showStats() {
        tvTotalSession.setText("Tổng số lần học: " + history.size());
        Set<Integer> quizSet = new HashSet<>();
        Set<String> daySet = new HashSet<>();
        int totalCorrect = 0, totalWrong = 0;
        long totalTime = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        for (StudySession s : history) {
            quizSet.add(s.getQuizId());
            daySet.add(sdf.format(s.getDate()));
            totalCorrect += s.getCorrectCount();
            totalWrong += (s.getTotalCount() - s.getCorrectCount());
            totalTime += s.getDurationMillis();
        }
        tvTotalQuiz.setText("Số quiz đã hoàn thành: " + quizSet.size());
        tvStreak.setText("Số ngày học khác nhau: " + daySet.size());
        tvTotalCorrect.setText("Tổng số câu đúng: " + totalCorrect);
        tvTotalWrong.setText("Tổng số câu sai: " + totalWrong);
        int totalQ = totalCorrect + totalWrong;
        String rate = totalQ > 0 ? (100 * totalCorrect / totalQ) + "%" : "0%";
        tvCorrectRate.setText("Tỉ lệ đúng: " + rate);
        String avgTime = history.size() > 0 ? (totalTime / history.size() / 1000) + " giây" : "0 giây";
        tvAvgTime.setText("Thời gian học TB: " + avgTime);
    }

    private void showChart() {
        // Thống kê số lần học theo ngày (7 ngày gần nhất)
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM", Locale.getDefault());
        HashMap<String, Integer> dayCount = new HashMap<>();
        for (StudySession s : history) {
            String day = sdf.format(s.getDate());
            dayCount.put(day, dayCount.getOrDefault(day, 0) + 1);
        }
        ArrayList<String> labels = new ArrayList<>(dayCount.keySet());
        Collections.sort(labels, (a, b) -> {
            try {
                Date da = sdf.parse(a);
                Date db = sdf.parse(b);
                return da.compareTo(db);
            } catch (Exception e) { return 0; }
        });
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < labels.size(); i++) {
            entries.add(new BarEntry(i, dayCount.get(labels.get(i))));
        }
        BarDataSet dataSet = new BarDataSet(entries, "Số lần học mỗi ngày");
        dataSet.setColor(Color.parseColor("#6200EE"));
        BarData data = new BarData(dataSet);
        data.setBarWidth(0.6f);
        barChart.setData(data);
        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labels.size());
        YAxis left = barChart.getAxisLeft();
        left.setAxisMinimum(0f);
        barChart.getAxisRight().setEnabled(false);
        barChart.invalidate();
    }

    private void showHistory() {
        // Hiển thị 5 lần học gần nhất
        List<StudySession> recent = new ArrayList<>();
        for (int i = history.size() - 1; i >= 0 && recent.size() < 5; i--) {
            recent.add(history.get(i));
        }
        historyAdapter = new HistoryAdapter(recent);
        rcvHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvHistory.setAdapter(historyAdapter);
    }

    // Adapter đơn giản cho lịch sử học gần nhất
    public static class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
        private final List<StudySession> list;
        public HistoryAdapter(List<StudySession> list) { this.list = list; }
        @NonNull
        @Override
        public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TextView tv = new TextView(parent.getContext());
            tv.setPadding(16, 16, 16, 16);
            tv.setTextSize(16);
            return new HistoryViewHolder(tv);
        }
        @Override
        public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
            StudySession s = list.get(position);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            String info = "Ngày: " + sdf.format(s.getDate()) + " | Quiz: " + s.getQuizId() + " | Đúng: " + s.getCorrectCount() + "/" + s.getTotalCount();
            ((TextView) holder.itemView).setText(info);
        }
        @Override
        public int getItemCount() { return list.size(); }
        public static class HistoryViewHolder extends RecyclerView.ViewHolder {
            public HistoryViewHolder(@NonNull View itemView) { super(itemView); }
        }
    }
} 