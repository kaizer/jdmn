
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision-with-extension.ftl", "componentwise"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "componentwise",
    label = "Componentwise",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Componentwise extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "componentwise",
        "Componentwise",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Componentwise() {
    }

    public List<type.Componentwise> apply(String a, String b, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((a != null ? asList(com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(a, java.math.BigDecimal[].class)) : null), (b != null ? asList(com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(b, java.math.BigDecimal[].class)) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'Componentwise'", e);
            return null;
        }
    }

    public List<type.Componentwise> apply(String a, String b, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply((a != null ? asList(com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(a, java.math.BigDecimal[].class)) : null), (b != null ? asList(com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(b, java.math.BigDecimal[].class)) : null), annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Componentwise'", e);
            return null;
        }
    }

    public List<type.Componentwise> apply(List<java.math.BigDecimal> a, List<java.math.BigDecimal> b, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(a, b, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public List<type.Componentwise> apply(List<java.math.BigDecimal> a, List<java.math.BigDecimal> b, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Start decision 'componentwise'
            long componentwiseStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments componentwiseArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            componentwiseArguments_.put("a", a);
            componentwiseArguments_.put("b", b);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, componentwiseArguments_);

            // Evaluate decision 'componentwise'
            List<type.Componentwise> output_ = evaluate(a, b, annotationSet_, eventListener_, externalExecutor_);

            // End decision 'componentwise'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, componentwiseArguments_, output_, (System.currentTimeMillis() - componentwiseStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'componentwise' evaluation", e);
            return null;
        }
    }

    protected List<type.Componentwise> evaluate(List<java.math.BigDecimal> a, List<java.math.BigDecimal> b, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return zip(asList("A", "B"), asList(a, b)).stream().map(x -> type.Componentwise.toComponentwise(x)).collect(Collectors.toList());
    }
}
