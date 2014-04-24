/*
 The MIT License

 Copyright (c) 2010-2013 Paul R. Holser, Jr.

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

package com.pholser.junit.quickcheck.generator.java.nio.charset;

import com.pholser.junit.quickcheck.Generating;
import com.pholser.junit.quickcheck.generator.BasicGeneratorTheoryParameterTest;

import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.List;
import java.util.TimeZone;

import static com.google.common.collect.Lists.newArrayList;
import static com.pholser.junit.quickcheck.Generating.*;
import static java.util.Arrays.*;
import static org.mockito.Mockito.*;

public class CharsetGeneratorTest extends BasicGeneratorTheoryParameterTest {
    private static final List<String> CHARSET_NAMES = newArrayList(Charset.availableCharsets().keySet());

    @Override protected void primeSourceOfRandomness() {
        when(Generating.ints(randomForParameterGenerator, 0, CHARSET_NAMES.size() - 1))
            .thenReturn(2).thenReturn(0).thenReturn(1);
    }

    @Override protected Type parameterType() {
        return Charset.class;
    }

    @Override protected int sampleSize() {
        return 3;
    }

    @Override protected List<?> randomValues() {
        return asList(Charset.forName(CHARSET_NAMES.get(2)), Charset.forName(CHARSET_NAMES.get(0)),
            Charset.forName(CHARSET_NAMES.get(1)));
    }

    @Override public void verifyInteractionWithRandomness() {
        verifyInts(randomForParameterGenerator, times(3), 0, CHARSET_NAMES.size() - 1);
    }
}
