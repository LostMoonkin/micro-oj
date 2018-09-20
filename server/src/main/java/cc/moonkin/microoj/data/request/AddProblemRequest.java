package cc.moonkin.microoj.data.request;

import cc.moonkin.microoj.data.Problem;
import cc.moonkin.microoj.data.TestCase;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author anchun
 *
 */
@Getter
@Setter
public class AddProblemRequest {

    private Problem problem;

    private List<TestCase> testCases = new ArrayList<>();
}
