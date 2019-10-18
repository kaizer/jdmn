/*
 * Copyright 2016 Goldman Sachs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.gs.dmn.transformation;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.serialization.DMNReader;
import com.gs.dmn.serialization.PrefixNamespaceMappings;
import com.gs.dmn.serialization.TypeDeserializationConfigurer;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.template.TemplateProvider;
import com.gs.dmn.validation.DMNValidator;
import org.omg.spec.dmn._20180521.model.TDefinitions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractDMNTransformer extends AbstractTemplateBasedTransformer {
    protected final DMNDialectDefinition dialectDefinition;
    protected final DMNReader dmnReader;
    protected final DMNValidator dmnValidator;
    protected final DMNTransformer dmnTransformer;
    protected final LazyEvaluationDetector lazyEvaluationDetector;
    protected final TypeDeserializationConfigurer typeDeserializationConfigurer;

    protected final String decisionBaseClass;
    protected final String javaRootPackage;

    public AbstractDMNTransformer(DMNDialectDefinition dialectDefinition, DMNValidator dmnValidator, DMNTransformer dmnTransformer, TemplateProvider templateProvider, LazyEvaluationDetector lazyEvaluationDetector, TypeDeserializationConfigurer typeDeserializationConfigurer, Map<String, String> inputParameters, BuildLogger logger) {
        super(templateProvider, inputParameters, logger);
        this.dialectDefinition = dialectDefinition;
        this.dmnTransformer = dmnTransformer;
        this.lazyEvaluationDetector = lazyEvaluationDetector;
        this.typeDeserializationConfigurer = typeDeserializationConfigurer;
        boolean xsdValidation = InputParamUtil.getOptionalBooleanParam(inputParameters, "xsdValidation");
        this.dmnReader = new DMNReader(logger, xsdValidation);
        this.dmnValidator = dmnValidator;

        this.javaRootPackage = InputParamUtil.getOptionalParam(inputParameters, "javaRootPackage");
        this.decisionBaseClass = dialectDefinition.getDecisionBaseClass();
    }

    protected DMNModelRepository readDMN(File file) {
        if (isDMNFile(file)) {
            Pair<TDefinitions, PrefixNamespaceMappings> result = dmnReader.read(file);
            DMNModelRepository repository = new DMNModelRepository(result.getLeft(), result.getRight());
            return repository;
        } else {
            throw new DMNRuntimeException(String.format("Invalid DMN file %s", file.getAbsoluteFile()));
        }
    }

    protected boolean isDMNFile(File file) {
        return file.isFile() && file.getName().endsWith(".dmn");
    }

    protected void handleValidationErrors(List<String> errors) {
        if (errors == null || errors.isEmpty()) {
            return;
        }

        for(String error: errors) {
            logger.error(error);
        }
        throw new IllegalArgumentException("Validation errors " + errors);
    }
}
