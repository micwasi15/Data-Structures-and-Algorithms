import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class GraphTests {
    @Test
    public void depthFirst_subgraph0() {
        var graph = createTestGraph();
        assertEquals("zero, two, one, four, three, five", graph.depthFirst("zero"));
    }

    @Test
    public void depthFirst_subgraph1() {
        var graph = createTestGraph();
        assertEquals("one", graph.depthFirst("one"));
    }

    @Test
    public void depthFirst_subgraph2() {
        var graph = createTestGraph();
        assertEquals("two, one", graph.depthFirst("two"));
    }

    @Test
    public void depthFirst_subgraph3() {
        var graph = createTestGraph();
        assertEquals("three, one, five, zero, two, four", graph.depthFirst("three"));
    }

    @Test
    public void depthFirst_subgraph4() {
        var graph = createTestGraph();
        assertEquals("four, two, one, three, five, zero", graph.depthFirst("four"));
    }

    @Test
    public void depthFirst_subgraph5() {
        var graph = createTestGraph();
        assertEquals("five, zero, two, one, four, three", graph.depthFirst("five"));
    }

    @Test
    public void depthFirst_subgraph6() {
        var graph = createTestGraph();
        assertEquals("six, seven, eight", graph.depthFirst("six"));
    }

    @Test
    public void depthFirst_subgraph7() {
        var graph = createTestGraph();
        assertEquals("seven, eight, six", graph.depthFirst("seven"));
    }

    @Test
    public void depthFirst_subgraph8() {
        var graph = createTestGraph();
        assertEquals("eight, six, seven", graph.depthFirst("eight"));
    }

    @Test
    public void depthFirst_subgraph9() {
        var graph = createTestGraph();
        assertEquals("nine, ten", graph.depthFirst("nine"));
    }

    @Test
    public void depthFirst_subgraph10() {
        var graph = createTestGraph();
        assertEquals("ten", graph.depthFirst("ten"));
    }

    @Test
    public void depthFirst_nodeDoesntExist() {
        var graph = createTestGraph();
        assertThrows(NoSuchElementException.class, () -> graph.depthFirst("doesn't exist"));
    }

    @Test
    public void breadthFirst_subgraph0() {
        var graph = createTestGraph();
        assertEquals("zero, two, four, one, three, five", graph.breadthFirst("zero"));
    }

    @Test
    public void breadthFirst_subgraph1() {
        var graph = createTestGraph();
        assertEquals("one", graph.breadthFirst("one"));
    }

    @Test
    public void breadthFirst_subgraph2() {
        var graph = createTestGraph();
        assertEquals("two, one", graph.breadthFirst("two"));
    }

    @Test
    public void breadthFirst_subgraph3() {
        var graph = createTestGraph();
        assertEquals("three, one, five, zero, two, four", graph.breadthFirst("three"));
    }

    @Test
    public void breadthFirst_subgraph4() {
        var graph = createTestGraph();
        assertEquals("four, two, one, three, five, zero", graph.breadthFirst("four"));
    }

    @Test
    public void breadthFirst_subgraph5() {
        var graph = createTestGraph();
        assertEquals("five, zero, two, four, one, three", graph.breadthFirst("five"));
    }

    @Test
    public void breadthFirst_subgraph6() {
        var graph = createTestGraph();
        assertEquals("six, seven, eight", graph.breadthFirst("six"));
    }

    @Test
    public void breadthFirst_subgraph7() {
        var graph = createTestGraph();
        assertEquals("seven, eight, six", graph.breadthFirst("seven"));
    }

    @Test
    public void breadthFirst_subgraph8() {
        var graph = createTestGraph();
        assertEquals("eight, six, seven", graph.breadthFirst("eight"));
    }

    @Test
    public void breadthFirst_subgraph9() {
        var graph = createTestGraph();
        assertEquals("nine, ten", graph.breadthFirst("nine"));
    }

    @Test
    public void breadthFirst_subgraph10() {
        var graph = createTestGraph();
        assertEquals("ten", graph.breadthFirst("ten"));
    }

    @Test
    public void breadthFirst_nodeDoesntExist() {
        var graph = createTestGraph();
        assertThrows(NoSuchElementException.class, () -> graph.breadthFirst("doesn't exist"));
    }

    @Test
    public void connectedComponents() {
        var graph = createTestGraph();
        assertEquals(3, graph.connectedComponents());
    }

    private static Graph<String> createTestGraph() {
        var edges = new LinkedList<Edge<String>>();

        edges.add(new Edge<>("zero", "two", 2));
        edges.add(new Edge<>("zero", "four", 5));
        edges.add(new Edge<>("four", "two", 3));
        edges.add(new Edge<>("four", "one", 6));
        edges.add(new Edge<>("two", "one", 4));
        edges.add(new Edge<>("four", "three", 1));
        edges.add(new Edge<>("three", "one", 3));
        edges.add(new Edge<>("three", "five", 4));
        edges.add(new Edge<>("five", "zero", 3));

        edges.add(new Edge<>("six", "seven", 3));
        edges.add(new Edge<>("seven", "eight", 2));
        edges.add(new Edge<>("eight", "six", 4));

        edges.add(new Edge<>("nine", "ten", 2));

        return new Graph<>(edges);
    }
}
