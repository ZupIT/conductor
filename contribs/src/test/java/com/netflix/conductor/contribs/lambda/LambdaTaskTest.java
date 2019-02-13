package com.netflix.conductor.contribs.lambda;

import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.run.Workflow;
import com.netflix.conductor.core.execution.WorkflowExecutor;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class LambdaTaskTest {

    private Workflow workflow = new Workflow();
    private WorkflowExecutor executor = mock(WorkflowExecutor.class);

    @Test
    public void startSuccessfully() throws Exception {
        LambdaTask scriptTask = new LambdaTask();

        Map inputObj = new HashMap();
        inputObj.put("a", 1);

        Task task = new Task();
        task.getInputData().put("input", inputObj);
        task.getInputData().put("lambdaExpression", "if ($.input.a==1){return {a:true}}else{return {a:false} } ");

        scriptTask.start(workflow, task, executor);

        assertEquals(Task.Status.COMPLETED, task.getStatus());
        assertEquals(Collections.singletonMap("a", true), task.getOutputData().get("result"));
    }

    @Test
    public void failCauseLambdaExpressionIsMissing() throws Exception {
        LambdaTask scriptTask = new LambdaTask();

        Task task = new Task();

        scriptTask.start(workflow, task, executor);

        assertEquals(Task.Status.FAILED, task.getStatus());
        assertNull(task.getOutputData().get("result"));
    }

    @Test
    public void failCauseLambdaExpressionEvaluationFailedWithSyntaxError() throws Exception {
        LambdaTask scriptTask = new LambdaTask();

        Map inputObj = new HashMap();
        inputObj.put("a", 1);
        Task task = new Task();
        task.getInputData().put("input", inputObj);
        task.getInputData().put("lambdaExpression", "if ($.input.a==1    {return {a:true}}else{return {a:false} } ");

        scriptTask.start(workflow, task, executor);

        assertEquals(Task.Status.FAILED, task.getStatus());
        assertNull(task.getOutputData().get("result"));
        assertNotNull(task.getOutputData().get("error"));

    }

    @Test
    public void failCauseLambdaExpressionEvaluationFailedWithLogicError() throws Exception {
        LambdaTask scriptTask = new LambdaTask();

        Map inputObj = new HashMap();
        inputObj.put("a", 1);
        Task task = new Task();
        task.getInputData().put("input", inputObj);
        task.getInputData().put("lambdaExpression", "if ($.input.a==1){console.log('yes')}else{console.log('no')}");

        scriptTask.start(workflow, task, executor);

        assertEquals(Task.Status.FAILED, task.getStatus());
        assertNull(task.getOutputData().get("result"));
        assertNotNull(task.getOutputData().get("error"));

    }

}
