package org.springone2gx.ast

import org.codehaus.groovy.control.MultipleCompilationErrorsException
import org.junit.Test

/**
 *
 * @author jbaruch
 * @since 13/04/2014
 */
@SuppressWarnings("GroovyAccessibility")
class AstTransformationsTests {

    final assertScript = new GroovyTestCase().&assertScript

    @Test
    void 'every class should have AUTHOR field'() {
        assertScript '''
                     class Foo {}
                     assert Foo.$AUTHOR == 'jbaruch'
                '''
    }

    @Test
    void 'message method should exist'() {
        assertScript '''
                import org.springone2gx.ast.Messenger

                def out = new ByteArrayOutputStream()
                System.out = new PrintStream(out)
                def content = new StringWriter()
                binding.out = new PrintWriter(content)

                @Messenger(shout = false)
                class QuietFoo { }

                new QuietFoo().message('Hello, world!')
                assert out.toString() == 'Hello, world!\\r\\n'
                out.reset()

                @Messenger(shout = true)
                class LoudFoo { }

                new LoudFoo().message('Hello, world!')
                assert out.toString() == 'HELLO, WORLD!\\r\\n'
                '''
    }

    @Test
    void 'main method should exist and call annotated method'() {
        assertScript '''
                    import org.springone2gx.ast.Main

                    def out = new ByteArrayOutputStream()
                    System.out = new PrintStream(out)
                    def content = new StringWriter()
                    binding.out = new PrintWriter(content)

                    class Foo {
                        @Main
                        def greet() {
                            println 'Hello, world!'
                        }
                    }

                    Foo.main(new String[0])
                    assert out.toString() == 'Hello, world!\\r\\n'
                    '''
    }

    @Test(expected = MultipleCompilationErrorsException)
    void 'only one main method should exist!'() {
        assertScript '''
                import org.springone2gx.ast.Main

                class Foo {

                    public static void main(String[] args){
                      throw new IllegalStateException('Wrong main, pal!')
                    }

                    @Main
                    def greet() {
                        println 'Hello, world!'
                    }
                }
                '''
    }

    @Test
    void "every class should have safe method"() {
        assertScript '''
            def nullObject = null;
            assert null == safe(nullObject.hashcode())
        '''
    }
}
