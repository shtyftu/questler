package net.shtyftu.ubiquode.controller;

import static net.shtyftu.ubiquode.controller.AController.LIST_PATH;
import static net.shtyftu.ubiquode.controller.AController.PROTO_CONTROLLER_PATH;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import net.shtyftu.ubiquode.dao.plain.QuestProtoDao;
import net.shtyftu.ubiquode.model.dump.QuestProtoDump;
import net.shtyftu.ubiquode.model.persist.simple.QuestProto;
import net.shtyftu.ubiquode.model.view.QuestProtoView;
import net.shtyftu.ubiquode.model.view.QuestProtoView.UpdateStatus;
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
@RequestMapping(PROTO_CONTROLLER_PATH)
public class QuestProtoController {

    private static final Gson GSON = new Gson();
    private final QuestProtoDao questProtoDao;

    @Autowired
    public QuestProtoController(QuestProtoDao questProtoDao) {
        this.questProtoDao = questProtoDao;
    }

    @RequestMapping(value = LIST_PATH, method = RequestMethod.GET)
    public Map<String, Object> getList() {
        final List<QuestProtoView> list = questProtoDao.getAll().stream()
                .map(QuestProtoView::new)
                .collect(Collectors.toList());
        return ImmutableMap.of("list", list);
    }

    //    @RequestMapping(value = "/export", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/export",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void exportFile(HttpServletResponse response) {
        try {
            final QuestProtoDump dump = new QuestProtoDump(questProtoDao.getAll());
            IOUtils.copy(new StringReader(GSON.toJson(dump)), response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {
            throw new RuntimeException("IOError writing file to output stream");
        }
    }

    @RequestMapping(value = "/import",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ModelAndView importFile(@RequestParam("file") MultipartFile dumpFile) {
        try {
            final String jsonString = IOUtils.toString(dumpFile.getInputStream());
            final QuestProtoDump dump = GSON.fromJson(jsonString, QuestProtoDump.class);
            final List<QuestProto> questProtoList = dump.getList();

            final Set<String> importedProtoIds = questProtoList.stream()
                    .map(QuestProto::getId)
                    .collect(Collectors.toSet());
            if (questProtoList.size() > importedProtoIds.size()) {
                throw new IllegalArgumentException("duplicated ids");
            }

            final Set<String> oldProtoIds = questProtoDao.getAllIds();
            questProtoDao.save(questProtoList);
            final List<QuestProtoView> listView = questProtoDao.getAll().stream()
                    .map(q -> {
                        UpdateStatus status = !importedProtoIds.contains(q.getId())
                                ? UpdateStatus.Old
                                : oldProtoIds.contains(q.getId())
                                        ? UpdateStatus.Updated
                                        : UpdateStatus.Created;
                        return new QuestProtoView(q, status);
                    }).collect(Collectors.toList());
            return new ModelAndView(
                    PROTO_CONTROLLER_PATH + LIST_PATH,
                    ImmutableMap.of("list", listView));
        } catch (IOException | IllegalArgumentException e) {
            return new ModelAndView(
                    PROTO_CONTROLLER_PATH + LIST_PATH,
                    ImmutableMap.of("errors", ImmutableList.of(e.getMessage())));
        }
    }

}
