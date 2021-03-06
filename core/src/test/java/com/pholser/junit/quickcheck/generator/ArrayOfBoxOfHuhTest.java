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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Iterables;
import com.pholser.junit.quickcheck.internal.generator.CoreTheoryParameterTest;
import com.pholser.junit.quickcheck.reflect.GenericArrayTypeImpl;
import com.pholser.junit.quickcheck.test.generator.Box;

import static com.pholser.junit.quickcheck.reflect.ParameterizedTypeImpl.*;
import static com.pholser.junit.quickcheck.reflect.WildcardTypeImpl.*;
import static org.mockito.Mockito.*;

public class ArrayOfBoxOfHuhTest extends CoreTheoryParameterTest {
    @Override protected void primeSourceOfRandomness() {
        int longIndex = 12;
        when(randomForParameterGenerator.nextLong()).thenReturn(1L).thenReturn(7L).thenReturn(63L);
        when(randomForGeneratorRepo.nextInt(0, Iterables.size(source) - 1)).thenReturn(longIndex);
    }

    @Override protected Type parameterType() {
        return new GenericArrayTypeImpl(parameterized(Box.class).on(extendsOf(Object.class)));
    }

    @Override protected int sampleSize() {
        return 3;
    }

    @Override protected List<?> randomValues() {
        List<Box<?>[]> values = new ArrayList<Box<?>[]>();
        values.add(new Box<?>[0]);
        values.add(new Box<?>[] { new Box<>(1L) });
        values.add(new Box<?>[] { new Box<>(7L), new Box<>(63L) });
        return values;
    }

    @Override public void verifyInteractionWithRandomness() {
        verify(randomForParameterGenerator, times(3)).nextLong();
        verifyNoMoreInteractions(randomForParameterGenerator);
        verify(randomForGeneratorRepo, times(3)).nextInt(0, Iterables.size(source) - 1);
        verifyNoMoreInteractions(randomForGeneratorRepo);
    }
}
