package net.shtyftu.ubiquode.controller;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.shtyftu.ubiquode.dao.simple.QuestListDao;
import net.shtyftu.ubiquode.dao.simple.QuestProtoDao;
import net.shtyftu.ubiquode.model.AModel;
import net.shtyftu.ubiquode.model.dump.QuestListDump;
import net.shtyftu.ubiquode.model.persist.simple.QuestProto;
import net.shtyftu.ubiquode.model.web.QuestListModel;
import net.shtyftu.ubiquode.model.persist.simple.QuestList;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author shtyftu
 */
@Controller
@RequestMapping("/list")
public class QuestListController {

    private static final Gson GSON = new Gson();
    private final QuestListDao questListDao;
    private final QuestProtoDao questProtoDao;

    @Autowired
    public QuestListController(QuestListDao questListDao, QuestProtoDao questProtoDao) {
        this.questListDao = questListDao;
        this.questProtoDao = questProtoDao;
    }


    @RequestMapping("/list")
    public ModelAndView getList(HttpServletRequest request) {
        final List<QuestListModel> list = questListDao.getAllKeys().stream()
                .map(questListDao::getByKey)
                .map(q -> new QuestListModel(
                        q.getKey(),
                        q.getKey(),
                        q.getQuestIdList().stream()
                                .map(questProtoDao::getByKey)
                                .map(AModel::toString)
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
        return new ModelAndView("list/list", ImmutableMap.of("listList", list));
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public void exportFile(String listId, HttpServletResponse response) {
        try {
            final List<QuestListDump> list = questListDao.getAllKeys().stream()
                    .map(questListDao::getByKey)
                    .map(q -> new QuestListDump(
                            q.getKey(),
                            q.getQuestIdList().stream()
                                    .map(questProtoDao::getByKey)
                                    .collect(Collectors.toList())))
                    .collect(Collectors.toList());
            IOUtils.copy(new StringReader(GSON.toJson(list)), response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {
            throw new RuntimeException("IOError writing file to output stream");
        }
    }

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public void importFile(QuestListDump dump) {
        questListDao.save(new QuestList(
                dump.getId(),
                dump.getList().stream()
                        .map(QuestProto::getKey)
                        .collect(Collectors.toList())));
        dump.getList().forEach(questProtoDao::save);
    }



}
