//
//  GCAsset.m
//
//  Created by Brandon Coston on 8/31/11.
//  Copyright 2011 Chute Corporation. All rights reserved.
//

#import "GCAsset.h"
#import "GCAssetUploader.h"
#import "GC_UIImage+Extras.h"

NSString * const GCAssetStatusChanged   = @"GCAssetStatusChanged";
NSString * const GCAssetProgressChanged = @"GCAssetProgressChanged";
NSString * const GCAssetUploadComplete = @"GCAssetUploadComplete";

@implementation GCAsset

@synthesize alAsset;
@synthesize thumbnail;
@synthesize selected;
@synthesize progress;
@synthesize status;
@synthesize parentID;

// Public: Checks whether or not the asset has been hearted
//
// Returns YES if the asset is hearted and NO if it isn't.
- (BOOL) isHearted {
    if([[GCAccount sharedManager] heartedAssets])
        return [[[GCAccount sharedManager] heartedAssets] containsObject:self];
    return NO;
}

#pragma mark - Heart Method
// Public: toggles the hearted state of an asset
//
// Returns the hearted state of the asset after toggling it.
- (BOOL) toggleHeart {
    if ([self isHearted]) {
        //unheart
        GCResponse *response = [self unheart];
        if ([response isSuccessful]) {
            [[[GCAccount sharedManager] heartedAssets] removeObject:self];
        }
    }
    else {
        //heart
        GCResponse *response = [self heart];
        if ([response isSuccessful]) {
            [[[GCAccount sharedManager] heartedAssets] addObject:self];
        }
    }
    return [self isHearted];
}

// Public: Same as isHearted method except it runs on a background thread and executes
// a block of code after it finishes
//
// aBoolBlock - A block of code to run when the method is done
//
// No return value.
- (void) toggleHeartInBackgroundWithCompletion:(GCBoolBlock) aBoolBlock {
    DO_IN_BACKGROUND_BOOL([self toggleHeart], aBoolBlock);
}

// Public: Hearts the asset
//
// Returns a GCResponse with the result of the heart request.
- (GCResponse *) heart {
    NSString *_path              = [[NSString alloc] initWithFormat:@"%@%@/%@/heart", API_URL, [[self class] elementName], [self objectID]];
    GCRequest *gcRequest         = [[GCRequest alloc] init];
    GCResponse *_response        = [[gcRequest postRequestWithPath:_path andParams:nil] retain];
    [gcRequest release];
    [_path release];
    return [_response autorelease];
}

// Public: Same as heart except it runs on a background thread and executes
// a block of code after the request is completed
//
// aBoolErrorBlock - A block of code to run when the method finishes
//
// No return value.
- (void) heartInBackgroundWithCompletion:(GCBoolErrorBlock) aBoolErrorBlock {
    DO_IN_BACKGROUND_BOOL_ERROR([self heart], aBoolErrorBlock);
}

// Public: Unhearts the asset
//
// Returns a GCResponse with the result of the unheart request.
- (GCResponse *) unheart {
    NSString *_path              = [[NSString alloc] initWithFormat:@"%@%@/%@/unheart", API_URL, [[self class] elementName], [self objectID]];
    GCRequest *gcRequest         = [[GCRequest alloc] init];
    GCResponse *_response        = [[gcRequest postRequestWithPath:_path andParams:nil] retain];
    [gcRequest release];
    [_path release];
    return [_response autorelease];
}

// Public: Same as unheart except it runs on a background thread and executes
// a block of code after the request is completed
//
// aBoolErrorBlock - A block of code to run when the method finishes
//
// No return value.
- (void) unheartInBackgroundWithCompletion:(GCBoolErrorBlock) aBoolErrorBlock {
    DO_IN_BACKGROUND_BOOL_ERROR([self unheart], aBoolErrorBlock);
}

// Public: Uses the asset's alAsset data to determine if the Asset exists on the server
// and whether or not it has already been uploaded
//
// Returns a GCResponse with it's object set to an array of dictionaries with info for each matching asset.
-(GCResponse*)verify{
    if(![self alAsset]){
        GCResponse *response = [[GCResponse alloc] init];
        NSMutableDictionary *_errorDetail = [NSMutableDictionary dictionary];
        [_errorDetail setValue:@"No asset info to to send" forKey:NSLocalizedDescriptionKey];
        [response setError:[GCError errorWithDomain:@"GCError" code:401 userInfo:_errorDetail]];
        return [response autorelease];
    }
    NSString *_path              = [[NSString alloc] initWithFormat:@"%@%@/verify", API_URL, [[self class] elementName]];
    GCRequest *gcRequest         = [[GCRequest alloc] init];
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObject:[[NSArray arrayWithObject:[self uniqueRepresentation]] JSONRepresentation] forKey:@"files"];
    GCResponse *_response        = [[gcRequest postRequestWithPath:_path andParams:params] retain];
    [gcRequest release];
    [_path release];
    return [_response autorelease];
}

#pragma mark - Comment Methods
- (GCResponse *) comments {
    if (IS_NULL([self parentID])) {
        return nil;
    }
    NSString *_path              = [[NSString alloc] initWithFormat:@"%@chutes/%@/assets/%@/comments", API_URL, [self parentID], [self objectID]];
    GCRequest *gcRequest         = [[GCRequest alloc] init];
    GCResponse *_response        = [[gcRequest getRequestWithPath:_path] retain];
    NSMutableArray *_comments    = [[NSMutableArray alloc] init]; 
    for (NSDictionary *_dic in [_response data]) {
        [_comments addObject:[GCComment objectWithDictionary:_dic]];
    }
    [_response setObject:_comments];
    [_comments release];
    [gcRequest release];
    [_path release];
    return [_response autorelease];
}

- (void) commentsInBackgroundWithCompletion:(GCResponseBlock) aResponseBlock {
    DO_IN_BACKGROUND([self comments], aResponseBlock);
}

- (GCResponse *) addComment:(NSString *) _comment {
    if (IS_NULL([self parentID])) {
        return nil;
    }
    NSMutableDictionary *_params = [[NSMutableDictionary alloc] init];
    [_params setValue:_comment forKey:@"comment"];
    
    NSString *_path             = [[NSString alloc] initWithFormat:@"%@chutes/%@/assets/%@/comments", API_URL, [self parentID], [self objectID]];
    
    GCRequest *gcRequest        = [[GCRequest alloc] init];
    GCResponse *_response       = [[gcRequest postRequestWithPath:_path andParams:_params] retain];
    [gcRequest release];
    [_path release];
    [_params release];
    return [_response autorelease];
}

- (void) addComment:(NSString *) _comment inBackgroundWithCompletion:(GCResponseBlock) aResponseBlock {
    DO_IN_BACKGROUND([self addComment:_comment], aResponseBlock);
}

- (NSDictionary *) uniqueRepresentation {
    if([self alAsset]){
        ALAssetRepresentation *_representation = [[self alAsset] defaultRepresentation];
        if([self objectID]){
            return [NSDictionary dictionaryWithObjectsAndKeys:[[_representation url] absoluteString], @"filename", 
                    [NSString stringWithFormat:@"%d", [_representation size]], @"size", 
                    [NSString stringWithFormat:@"%d", [_representation size]], @"md5",
                    [self objectID], @"id",
                    [NSNumber numberWithInt:[self status]], @"status",
                    nil];
        }
        return [NSDictionary dictionaryWithObjectsAndKeys:[[_representation url] absoluteString], @"filename", 
                [NSString stringWithFormat:@"%d", [_representation size]], @"size", 
                [NSString stringWithFormat:@"%d", [_representation size]], @"md5", 
                nil];
    }
    return nil;
}

- (NSString *) uniqueURL {
    return [[[[self alAsset] defaultRepresentation] url] absoluteString];
}

#pragma mark - Upload

- (void) upload {
    [[GCAssetUploader sharedUploader] addAsset:self];
}

#pragma mark - Accessors Override
- (UIImage *) thumbnail {
    if ([self alAsset]) {
        return [UIImage imageWithCGImage:[[self alAsset] thumbnail]];
    }
    else if([self status] == GCAssetStateFinished) {
        return [self imageForWidth:75 andHeight:75];
    }
    return nil;
}

- (void) setProgress:(CGFloat)aProgress {
    progress = aProgress;
    [[NSNotificationCenter defaultCenter] postNotificationName:GCAssetProgressChanged object:self];
}

- (void) setStatus:(GCAssetStatus)aStatus {
    if (status == GCAssetStateCompleting && aStatus == GCAssetStateFinished) {
        status = aStatus;
        [[NSNotificationCenter defaultCenter] postNotificationName:GCAssetUploadComplete object:self];
    }
    
    status = aStatus;
    [[NSNotificationCenter defaultCenter] postNotificationName:GCAssetStatusChanged object:self];
}

- (NSString*)urlStringForImageWithWidth:(NSUInteger)width andHeight:(NSUInteger)height{
    if ([self status] == GCAssetStateNew)
        return nil;
    
    NSString *urlString = [self objectForKey:@"url"];
    
    if(urlString)
        urlString   = [urlString stringByAppendingFormat:@"/%dx%d",width,height];
    return urlString;
}

- (UIImage *)imageForWidth:(NSUInteger)width andHeight:(NSUInteger)height{
    if ([self alAsset]) {
        UIImage *fullResolutionImage = [UIImage imageWithCGImage:[[[self alAsset] defaultRepresentation] fullResolutionImage] scale:1 orientation:[[[self alAsset] valueForProperty:ALAssetPropertyOrientation] intValue]];
        return [fullResolutionImage imageByScalingProportionallyToSize:CGSizeMake(width, height)];
    }
    
    NSString *urlString = [self urlStringForImageWithWidth:width andHeight:height];
    
    NSURL *url      = [NSURL URLWithString:urlString];
    NSData *data    = [NSData dataWithContentsOfURL:url];
    UIImage *image  = nil;
    
    if(data)
        image = [UIImage imageWithData:data];
    return image;
}

- (void)imageForWidth:(NSUInteger)width 
            andHeight:(NSUInteger)height 
inBackgroundWithCompletion:(void (^)(UIImage *))aResponseBlock {    
    DO_IN_BACKGROUND([self imageForWidth:width andHeight:height], aResponseBlock);
}

- (NSDate*)createdAt{
    if(self.alAsset){
        return [self.alAsset valueForProperty:ALAssetPropertyDate];
    }
    return [super createdAt];
}

#pragma mark - Memory Management
- (id) init {
    self = [super init];
    if (self) {
        [self setStatus:GCAssetStateNew];
    }
    return  self;
}

- (id) initWithDictionary:(NSDictionary *) dictionary {
    self = [super initWithDictionary:dictionary];
    if (self) {
        if([dictionary objectForKey:@"status"])
            [self setStatus:[[dictionary objectForKey:@"status"] intValue]];
        else
            [self setStatus:GCAssetStateFinished];
        if(![self objectID]){
            if([self objectForKey:@"asset_id"])
                [self setObject:[self objectForKey:@"asset_id"] forKey:@"id"];
        }
    }
    return self;
}

- (void) dealloc {
    [parentID release];
    [alAsset release];
    [super dealloc];
}

#pragma mark - Super Class Methods
+ (NSString *)elementName {
    return @"assets";
}

- (BOOL) isEqual:(id)object {
    if (IS_NULL([self objectID]) && IS_NULL([object objectID])) {
        if([self alAsset] && [object alAsset])
            return [[self alAsset] isEqual:[object alAsset]];
        else
            return [super isEqual:object];
    }
    if (IS_NULL([self objectID]) || IS_NULL([object objectID])) {
        return NO;
    }
    
    if ([[self objectID] intValue] == [[object objectID] intValue]) {
        return YES;
    }
    return NO;
}

-(NSUInteger)hash{
    if(IS_NULL([self objectID])){
        if([self alAsset])
            return [[self alAsset] hash];
        else
            return [super hash];
    }
    return [[self objectID] hash];
}

@end
