package com.fastretailing.marketingpf.controllers.mastervalue.get;

import com.fastretailing.marketingpf.tests.JsonAssert;
import org.junit.jupiter.api.Test;

class MasterValueGetResponseTest {

    @Test
    public void expectingSuccess() throws Exception {
        MasterValueGetResponse response = new MasterValueGetResponse("{\"choicesList\": [{\"name\":\"10y\", \"value\": \"10\"},{\"name\":\"20y\", \"value\": \"20\"}]}");
        JsonAssert.assertJsonOutput(response).isSameContentAs("{"
                + "    \"choicesList\": {"
                + "        \"choicesList\": [{"
                + "                \"name\": \"10y\","
                + "                \"value\": \"10\""
                + "            }, {"
                + "                \"name\": \"20y\","
                + "                \"value\": \"20\""
                + "            }"
                + "        ]"
                + "    }"
                + "}");
    }
}
