/*
 * The MIT License
 *
 * Copyright 2017 Jeff Pearce (jxpearce@godaddy.com).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.jenkinsci.plugins.githubautostatus;

import com.google.common.collect.ImmutableMap;
import java.io.IOException;
import java.util.Map;
import org.kohsuke.github.GHCommitState;
import org.kohsuke.github.GHRepository;

/**
 *
 * @author jxpearce
 */
/**
 * BuildStatus can send status to GitHub for a particular commit
 * @author jxpearce
 */
public class BuildStatus {
    
    public BuildStatus(String shaString, String targetUrl, String context) {
        this.shaString = shaString;
        this.targetUrl = targetUrl;
        this.context = context;
        commitState = GHCommitState.PENDING;
    }
    
    static final Map<GHCommitState, String> DESCRIPTION_MAP = ImmutableMap.of(
        GHCommitState.PENDING, "Building stage",
        GHCommitState.FAILURE, "Failed to build stage",
        GHCommitState.ERROR, "Failed to build stage",
        GHCommitState.SUCCESS, "Stage built successfully");    
    
    private final String context;
    
    private final String shaString;
    
    private GHCommitState commitState;
    
    private final String targetUrl;

    /**
     * Gets the context for the commit
     * @return the context
     */
    public String getContext() {
        return context;
    }

    /**
     * Gets the current commit state
     * @return the commitState
     */
    public GHCommitState getCommitState() {
        return commitState;
    }

    /**
     * Sets the commit state and sends the new state to GitHub
     * @param repository the repository to notify
     * @param commitState the commitState to set
     * @throws java.io.IOException downstream error
     */
    public void setCommitState(GHRepository repository, GHCommitState commitState) throws IOException {
        this.commitState = commitState;
        repository.createCommitStatus(shaString, commitState, targetUrl, DESCRIPTION_MAP.get(commitState), context);
    }
    
}
