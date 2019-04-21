package es.nitaur;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QuizFormApiIT {

    private static final String QUIZ_API_BASE_URL = "/api/quiz";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getForms() throws Exception {
        this.mockMvc.perform(get(QUIZ_API_BASE_URL + "/1/form"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.id==0)]").exists())
                .andExpect(jsonPath("$[?(@.id==1)]").exists())
                .andExpect(jsonPath("$[?(@.id==0)].language", hasItem("EN")))
                .andExpect(jsonPath("$[?(@.id==0)].section.id", hasItem(1)))
                .andExpect(jsonPath("$[?(@.id==1)].section.id", hasItem(2)))
                .andExpect(jsonPath("$[?(@.id==0)].section.childSections.length()", hasItem(0)))
                .andExpect(jsonPath("$[?(@.id==0)].section.quizQuestions[?(@.id==1)]").exists())
                .andExpect(jsonPath("$[?(@.id==0)].section.quizQuestions[?(@.id==2)]").exists())
                .andExpect(jsonPath("$[?(@.id==0)].section.quizQuestions[?(@.id==1)].question", hasItem("What is your first name?")))
                .andExpect(jsonPath("$[?(@.id==0)].section.quizQuestions[?(@.id==1)].answers.length()", hasItem(0)));
    }

    @Test
    public void createForm() throws Exception {
        String language = "RU";
        JSONObject formJSON = getFormJson(language);

        long id = createForm(formJSON);

        this.mockMvc.perform(get(QUIZ_API_BASE_URL + "/1/form"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.id==" + id + ")]").exists())
                .andExpect(jsonPath("$[?(@.id==" + id + ")].language", hasItem(language)));
    }

    @Test
    public void updateForm() throws Exception {
        String language = "RU";
        JSONObject formJSON = getFormJson(language);

        long id = createForm(formJSON);

        String question = "What is the meaning of life?";
        String question42 = "What is the meaning of life? 42?";
        String newLanguage = "ES";

        updateFormJSON(formJSON, question, question42, newLanguage);

        this.mockMvc.perform(put(QUIZ_API_BASE_URL + "/form/" + id)
                .content(formJSON.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        this.mockMvc.perform(get(QUIZ_API_BASE_URL + "/1/form"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.id==" + id + ")]").exists())
                .andExpect(jsonPath("$[?(@.id==" + id + ")].language", hasItem(newLanguage)))
                .andExpect(jsonPath("$[?(@.id==" + id + ")].section.quizQuestions[?(@.question=='" + question + "')]").exists())
                .andExpect(jsonPath("$[?(@.id==" + id + ")].section.childSections[*].quizQuestions[?(@.question=='" + question42 + "')]").exists());
    }

    @Test
    public void deleteForm() throws Exception {
        String language = "RU";
        JSONObject formJSON = getFormJson(language);
        long id = createForm(formJSON);
        this.mockMvc.perform(delete(QUIZ_API_BASE_URL + "/form/" + id))
                .andExpect(status().isNoContent());

        this.mockMvc.perform(get(QUIZ_API_BASE_URL + "/1/form"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.id=="+ id + ")]").doesNotExist());
    }

    private JSONObject getFormJson(String language) throws JSONException {
        JSONObject sectionJSON = new JSONObject()
                .put("quizQuestions", new JSONArray())
                .put("childSections", new JSONArray());
        return new JSONObject()
                .put("language", language)
                .put("section", sectionJSON);
    }

    private long createForm(JSONObject formJSON) throws Exception {
        MvcResult result = this.mockMvc.perform(post(QUIZ_API_BASE_URL + "/1/form")
                .content(formJSON.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        return new JSONObject(result.getResponse().getContentAsString()).getLong("id");
    }

    private void updateFormJSON(JSONObject formJSON,
                                String question,
                                String childSectionQuestion,
                                String newLanguage) throws JSONException {
        JSONObject quizQuestionJSON = new JSONObject()
                .put("question", question);
        JSONObject childSectionQuestionJSON = new JSONObject()
                .put("question", childSectionQuestion);

        JSONObject childSectionJSON = new JSONObject()
                .put("quizQuestions", new JSONArray().put(childSectionQuestionJSON))
                .put("childSections", new JSONArray());

        formJSON.getJSONObject("section")
                .getJSONArray("quizQuestions")
                .put(quizQuestionJSON);
        formJSON.getJSONObject("section")
                .getJSONArray("childSections")
                .put(childSectionJSON);
        formJSON.put("language", newLanguage);
    }
}
