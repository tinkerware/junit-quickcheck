/*
 The MIT License

 Copyright (c) 2010-2014 Paul R. Holser, Jr.

 Permission is hereby granted, free of charge, to any person obtaining
 a copy of this software and associated documentation files (the
 "Software"), to deal in the Software without restriction, including
 without limitation the rights to use, copy, modify, merge, publish,
 distribute, sublicense, and/or sell copies of the Software, and to
 permit persons to whom the Software is furnished to do so, subject to
 the following conditions:

 The above copyright notice and this permission notice shall be
 included in all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.pholser.junit.quickcheck.generator;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.pholser.junit.quickcheck.internal.generator.GeneratorRepository;
import org.javaruntype.type.TypeParameter;

import static java.util.Collections.*;

/**
 * Produces values for theory parameters of types that have components that would also need generation, such as arrays,
 * lists, and predicates.
 *
 * @param <T> type of theory parameter to apply this generator's values to
 */
public abstract class ComponentizedGenerator<T> extends Generator<T> {
    private final List<Generator<?>> components = new ArrayList<>();

    /**
     * @param type class token for type of theory parameter this generator is applicable to
     */
    protected ComponentizedGenerator(Class<T> type) {
        super(type);
    }

    @Override public final boolean hasComponents() {
        return true;
    }

    @Override public void addComponentGenerators(List<Generator<?>> newComponents) {
        components.clear();
        components.addAll(newComponents);
    }

    public List<Generator<?>> componentGenerators() {
        return unmodifiableList(components);
    }

    @Override public boolean canGenerateForParametersOfTypes(List<TypeParameter<?>> typeParameters) {
        return numberOfNeededComponents() == typeParameters.size();
    }

    @Override public void configure(Map<Class<? extends Annotation>, Annotation> configurationsByType) {
        for (Generator<?> each : components)
            each.configure(configurationsByType);
    }

    @Override public void provideRepository(GeneratorRepository provided) {
        super.provideRepository(provided);
        for (Generator<?> each : components)
            each.provideRepository(provided);
    }
}
