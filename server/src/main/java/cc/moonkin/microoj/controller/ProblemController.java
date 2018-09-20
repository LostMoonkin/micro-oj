package cc.moonkin.microoj.controller;

import cc.moonkin.microoj.annotation.Auth;
import cc.moonkin.microoj.constant.api.ProblemApi;
import cc.moonkin.microoj.data.Problem;
import cc.moonkin.microoj.data.enums.Role;
import cc.moonkin.microoj.data.request.AddProblemRequest;
import cc.moonkin.microoj.log.MicroLog;
import cc.moonkin.microoj.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author anchun
 *
 */
@RestController
public class ProblemController {

    private static final MicroLog LOG = MicroLog.getLogger(ProblemController.class);

    @Autowired
    private ProblemService problemService;

    @GetMapping(ProblemApi.PROBLEM_API)
    @Auth(role = Role.SYSTEM_ADMIN)
    public Boolean addProblem(@RequestBody final AddProblemRequest problemRequest,
            final HttpServletResponse response) {
        if (problemRequest.getProblem() == null) {
            LOG.message("addProblem - request must contain a problem")
                    .warn();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return false;
        }
        final Problem problem = problemRequest.getProblem();
        if (!problem.isSpj() && CollectionUtils.isEmpty(problemRequest.getTestCases())) {
            LOG.message("addProblem - normal problem must contain testCases")
                    .with("problem", problem)
                    .warn();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return false;
        }
        return problemService.addProblem(problem, problemRequest.getTestCases());
    }
}
