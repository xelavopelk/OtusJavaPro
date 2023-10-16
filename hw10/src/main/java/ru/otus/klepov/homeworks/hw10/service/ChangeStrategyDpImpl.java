package ru.otus.klepov.homeworks.hw10.service;

import ru.otus.klepov.homeworks.hw10.domain.MoneyPack;
import ru.otus.klepov.homeworks.hw10.domain.MoneyPackFactory;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.min;

/*
Размен банкнот через DP
 */
public class ChangeStrategyDpImpl implements ChangeStrategy {

    private MoneyPackFactory mpf;

    public ChangeStrategyDpImpl(MoneyPackFactory mpf) {
        this.mpf = mpf;
    }

    private Optional<List<Integer>> makeNoteList(Optional<List<Integer>> proposeChange, Integer note) {
        List<Integer> tmp = proposeChange.get().stream().collect(Collectors.toList()); //копирование результата, чтобы добавить в нужный
        tmp.add(note);
        return Optional.of(tmp);
    }

    private Optional<List<Integer>> newNoteList(Optional<List<Integer>> oldNoteList, Optional<List<Integer>> proposeChange, Integer note) {
        if (proposeChange.isEmpty()) {
            return oldNoteList;
        } else {
            if (oldNoteList.isEmpty()) {
                return makeNoteList(proposeChange, note);
            } else if (oldNoteList.get().size() > proposeChange.get().size()) {
                return makeNoteList(proposeChange, note);
            } else {
                return oldNoteList;
            }
        }
    }

    private Optional<List<Integer>> getChangeInner(Map<Integer, Integer> boxes, int summa, Map<Integer, List<Integer>> sums) {
        if (sums.containsKey(summa)) {
            if (!boxes.containsKey(summa)) {
                return Optional.of(sums.get(summa));
            } else {
                return boxes.get(summa) > 0 ? Optional.of(List.of(summa)) : Optional.empty();
            }
        } else {
            Optional<List<Integer>> selChange = Optional.empty();
            for (var note : boxes.keySet()) {
                if (note <= summa && boxes.get(note) > 0) {
                    boxes.computeIfPresent(note, (k, v) -> v - 1);
                    var res = getChangeInner(boxes, summa - note, sums);
                    boxes.computeIfPresent(note, (k, v) -> v + 1);
                    selChange = newNoteList(selChange, res, note);
                }
            }
            if (selChange.isPresent()) {
                sums.put(summa, selChange.get());
                return Optional.of(sums.get(summa));
            } else {
                return Optional.empty();
            }
        }
    }

    private List<MoneyPack> list2MoneyPack(List<Integer> arr) {
        return arr.stream()
                .collect(Collectors.groupingBy(x -> x, Collectors.counting()))
                .entrySet()
                .stream().map(x -> mpf.create(x.getKey(), x.getValue().intValue()))
                .collect(Collectors.toList());

    }

    @Override
    public Optional<List<MoneyPack>> getChange(List<MoneyPack> boxes, int summa) {
        if (boxes.size() < 1 || summa < 1) {
            return Optional.empty();
        } else {
            var sums = boxes.stream().collect(Collectors.toMap(x -> x.getNominal(), x -> List.of(x.getNominal())));
            var dict = boxes.stream().collect(Collectors.toMap(x -> x.getNominal(), x -> x.getCount()));
            return getChangeInner(dict, summa, sums).map(x -> list2MoneyPack(x));
        }
    }
}
