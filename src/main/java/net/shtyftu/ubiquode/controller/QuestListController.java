package net.shtyftu.ubiquode.controller;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
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
    public ModelAndView getList() {
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
        return new ModelAndView("list/list", ImmutableMap.of("list", list));
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public void exportFile(String listId, HttpServletResponse response) {
        try {
            final QuestList questList = questListDao.getByKey(listId);
            final QuestListDump dump = new QuestListDump(
                    questList.getKey(),
                    questList.getQuestIdList().stream()
                            .map(questProtoDao::getByKey)
                            .collect(Collectors.toList()));
            IOUtils.copy(new StringReader(GSON.toJson(dump)), response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {
            throw new RuntimeException("IOError writing file to output stream");
        }
    }

    @RequestMapping(value = "/import",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public void importFile(MultipartFile file, HttpServletRequest request) {
    public ModelAndView importFile(@RequestParam("file") MultipartFile dumpFile) {
        try {
            final String jsonString = IOUtils.toString(dumpFile.getInputStream());
            final TypeToken<List<QuestListDump>> listType = new TypeToken<List<QuestListDump>>() {};
            final List<QuestListDump> questListDumpList = GSON.fromJson(jsonString, listType.getType());
            questListDumpList.forEach(dump -> {
                questListDao.save(new QuestList(
                        dump.getId(),
                        dump.getList().stream()
                                .map(QuestProto::getKey)
                                .collect(Collectors.toList())));
                dump.getList().forEach(questProtoDao::save);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getList();
    }



}
