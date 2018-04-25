package io.jenkins.plugins.sample;

import hudson.Launcher;

//import hudson.tasks.test.TestResult;
//import hudson.tasks.test.AbstractTestResultAction;
//import hudson.tasks.test.AbstractTestResultAction.*;
//import hudson.tasks.test.AggregatedTestResultAction;
//import hudson.tasks.test.AggregatedTestResultAction.ChildReport;

import hudson.Extension;
import hudson.FilePath;
import hudson.util.FormValidation;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.Action;
import hudson.model.BuildListener;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.Builder;
import hudson.tasks.Recorder;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Collection;

import jenkins.tasks.SimpleBuildStep;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundSetter;

public class HelloWorldBuilder extends Builder implements SimpleBuildStep {

    private final String name;
    private boolean useFrench;

    @DataBoundConstructor
    public HelloWorldBuilder(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isUseFrench() {
        return useFrench;
    }

    @DataBoundSetter
    public void setUseFrench(boolean useFrench) {
        this.useFrench = useFrench;
    }

    @Override
    public void perform(Run<?, ?> run, FilePath workspace, Launcher launcher, TaskListener listener) throws InterruptedException, IOException {
        if (useFrench) {
            listener.getLogger().println(name);
        } else {
            listener.getLogger().println(name);
        }
		{
		//Date date = new Date() ;
		//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
		//File file = new File(dateFormat.format(date) + ".tsv") ;
		//BufferedWriter out = Files.newWriter(file, StandardCharsets.UTF_8);

		//Get current test results
		//Also look up AbstractTestResultAction
		AggregatedTestResultAction currentTestResults = run.getAction(AggregatedTestResultAction.class);

		//retrieve the test results 
		List<AggregatedTestResultAction.ChildReport> currentResults = currentTestResults.getResult();

                listener.getLogger().println("Running Test Checker Plugin");

		//listener.getLogger().println("Current Build :- ", currentTestResults.getDisplayName());
                //listener.getLogger().println("Previous Build :- ", currentTestResults.getDisplayName());


		//iterate through the result of each test
		for(int i = 0; i < currentResults.size(); i++)
		{

			//obtain the report of a test	
			AggregatedTestResultAction.ChildReport child = currentResults.get(i);

			//retreive the test result
			TestResult currentTestResultChild = (TestResult)child.result;

			//get the passed tests 
			ArrayList<TestResult> currRes = ((ArrayList<TestResult>)currentTestResultChild.getPassedTests());

			//iterate through each passed test
			for(int j = 0; j < currRes.size(); j++)
			{
				//obtain the status of the test in current build
				TestResult currentTestResChild = currRes.get(j);

				//obtain the status of the test in previous build
				TestResult previousTestResChild = currRes.get(j).getPreviousResult();

				// Check previous test result isnt null
				if (previousTestResChild == null) continue;
	
				// Case 1: Both passed
				if (currentTestResChild.isPassed() && previousTestResChild.isPassed())
				{
					listener.getLogger().println("No Change (Passed) : " + currentTestResChild.getDisplayName());
					//out.write("No Change (Passed) : " + currentTestResChild.getDisplayName());
				}
				// Case 2: Previous failed, newly passed
				if (currentTestResChild.isPassed() && !previousTestResChild.isPassed())
				{

					//out.write("Newly Passing Test : " + currentTestResChild.getDisplayName());
					listener.getLogger().println("Newly Passing Test : " + currentTestResChild.getDisplayName());
				}
			}

			//get the failed tests 
			ArrayList<TestResult> currResF = ((ArrayList<TestResult>)currentTestResultChild.getFailedTests());

			//iterate through each failed test
			for(int k = 0; k < currResF.size(); k++)
			{
				//obtain the status of the test in current build
				TestResult currentTestResChild = currResF.get(k);

				//obtain the status of the test in previous build
				TestResult previousTestResChild = currResF.get(k).getPreviousResult();
				// Check previous test result isnt null
				if (previousTestResChild == null) continue;

				// Case 3: Both failed
				if (!currentTestResChild.isPassed() && !previousTestResChild.isPassed())
				{
					//out.write("No Change (Failed) : " + currentTestResChild.getDisplayName());
					listener.getLogger().println("No Change (Failed) : " + currentTestResChild.getDisplayName());
				}       
				// Case 4: Previous passed, newly failed
				if (!currentTestResChild.isPassed() && previousTestResChild.isPassed())
				{
					out.write("Newly Failing Test : " + currentTestResChild.getDisplayName());
					listener.getLogger().println("Newly Failing Test : " + currentTestResChild.getDisplayName());
				}
			}

		}

		//obtain the information from previous build
		//Run<?, ?> previousBuiltBuild = run.getPreviousBuiltBuild();


		// try to read the test suite file from the workspace
		//InputStream inputStream = workspace.child(testSuiteFile).read();
		//out.close();
	}
    }

    @Symbol("greet")
    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {

        public FormValidation doCheckName(@QueryParameter String value, @QueryParameter boolean useFrench)
                throws IOException, ServletException {
            if (value.length() == 0)
                return FormValidation.error(Messages.HelloWorldBuilder_DescriptorImpl_errors_missingName());
            if (value.length() < 4)
                return FormValidation.warning(Messages.HelloWorldBuilder_DescriptorImpl_warnings_tooShort());
            if (!useFrench && value.matches(".*[éáàç].*")) {
                return FormValidation.warning(Messages.HelloWorldBuilder_DescriptorImpl_warnings_reallyFrench());
            }
            return FormValidation.ok();
        }

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            return true;
        }

        @Override
        public String getDisplayName() {
            return Messages.HelloWorldBuilder_DescriptorImpl_DisplayName();
        }

    }

	@Override
	public boolean prebuild(AbstractBuild<?, ?> build, BuildListener listener) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean perform(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener)
			throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Action getProjectAction(AbstractProject<?, ?> project) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<? extends Action> getProjectActions(AbstractProject<?, ?> project) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BuildStepMonitor getRequiredMonitorService() {
		// TODO Auto-generated method stub
		return BuildStepMonitor.NONE;
	}

}
