/**
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
package com.gs.dmn.feel.analysis.syntax.ast.expression.function;

import com.gs.dmn.feel.analysis.semantics.environment.Conversion;
import com.gs.dmn.feel.analysis.semantics.environment.ConversionKind;
import com.gs.dmn.feel.analysis.semantics.type.ListType;
import com.gs.dmn.feel.analysis.semantics.type.NamedType;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.runtime.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class NamedSignature extends Signature {
    private final Map<String, Type> namedTypes;

    public NamedSignature(Map<String, Type> namedTypes) {
        this.namedTypes = namedTypes;
    }

    @Override
    public int size() {
        return namedTypes == null ? 0 : namedTypes.size();
    }

    @Override
    public boolean compatible(List<FormalParameter> parameters) {
        if (size() != parameters.size()) {
            return false;
        }
        for (FormalParameter formalParameter : parameters) {
            Type argumentType = namedTypes.get(formalParameter.getName());
            Type parameterType = formalParameter.getType();
            if (!argumentType.conformsTo(parameterType)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<Pair<Signature, List<Conversion>>> candidates() {
        // TODO extends to all parameters and conversions
        if (size() == 1) {
            Map.Entry<String, Type> entry = namedTypes.entrySet().iterator().next();
            String name = entry.getKey();
            Type type = entry.getValue();
            if (type instanceof ListType) {
                Type elementType = ((ListType) type).getElementType();
                List<Conversion> conversions = Arrays.asList(new Conversion(ConversionKind.LIST_TO_ELEMENT, elementType));
                Map<String, Type> newSig = new LinkedHashMap<>();
                newSig.put(name, type);
                NamedSignature newSignature = new NamedSignature(newSig);
                return Arrays.asList(new Pair(newSignature, conversions));
            }
        }
        return new ArrayList<>();
    }

    public Type getType(String name) {
        return namedTypes.get(name);
    }

    @Override
    public String toString() {
        String opd = namedTypes.keySet().stream().map(k -> String.format("%s : %s", k, namedTypes.get(k).toString())).collect(Collectors.joining(", "));
        return String.format("NamedSignature(%s)", opd);
    }
}
